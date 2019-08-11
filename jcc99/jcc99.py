#!/usr/bin/python

import lldb
import commands
from optparse import OptionParser
import shlex
import struct

ARRAY_ID = 1
BOOLEAN_ID = 2
CHARACTER_ID = 3
CLASS_ID = 4
DOUBLE_ID = 5
FLOAT_ID = 6
INTEGER_ID = 7
LONG_ID = 8
METHOD_ID = 9
SHORT_ID = 10
VOID_ID = 11


class Jcc99:

  def __init__(self):
    pass
  
  def do(self, debugger, command, result, internal_dict):
    target = debugger.GetSelectedTarget()
    process = target.GetProcess()
    thread = process.GetSelectedThread()
    frame = thread.GetSelectedFrame()
    module = frame.GetModule()
    error = lldb.SBError()
    parser = OptionParser(usage="do [-f] address",prog="do")
    parser.add_option("-f", action="store_true", dest="full", default=False, help="also display fields from superclasses")
    (options, args) = parser.parse_args(command.split())
    if len(args) != 1:
      parser.error("incorrect number of arguments")
    if args[0].startswith("0x"):
      addr=int(args[0],base=16)
    else:
      addr=int(args[0])
    p=process.ReadUnsignedFromMemory(addr, 4, error)
    vector = lookup(p, module)
    if vector.GetName().startswith("Vector_"):
      className=vector.GetName()[7:]
      if options.full:
        while True:
          dumpFields(p,className,module,process)
          info=module.FindSymbol("Info_" + className)
          infoAddress=info.GetStartAddress().__int__()
          parentAddress=process.ReadUnsignedFromMemory(infoAddress+8, 4, error)
          if parentAddress==0:
            break
          parent=lookup(parentAddress,module)
          if not parent.GetName().startswith("Vector_"):
            break
          className=parent.GetName()[7:]
      else:
        dumpFields(p,className,module,process)
  
  def cf(self, debugger, command, result, internal_dict):
    target = debugger.GetSelectedTarget()
    process = target.GetProcess()
    thread = process.GetSelectedThread()
    frame = thread.GetSelectedFrame()
    module = frame.GetModule()
    error = lldb.SBError()
#
# Get pc and lookup where we are
#     
    pc = int(frame.FindRegister("pc").value, 0)
    fp = int(frame.FindRegister("ebp").value, 0)
    
    while True:
      here = lookup(pc, module)
      if here is None:
        break
      else:
        offset = pc - here.GetStartAddress().__int__()
        #
        # Check if we are in a Method
        #
        if here.GetName().startswith("Method_"):
          #
          # Try to find the debug info for this symbol
          #
          debug = module.FindSymbol("Debug_" + here.GetName())
          if not debug.IsValid():
            pack = "(?)"
          else:
            pack = "(" + getParameterPack(debug, fp, process) + ")"
        else:
          pack = ""
        print here.GetName() + "+" + str(offset) + pack   
        pc = process.ReadUnsignedFromMemory(fp + 4, 4, error)
        if error.Fail():
          break
        fp = process.ReadUnsignedFromMemory(fp, 4, error)
        if error.Fail():
          break

         
def lookup(address, module):
  for symbol in module:
    if symbol.GetStartAddress().__int__() <= address and symbol.GetEndAddress().__int__() > address:
        return symbol
  return None


def getTypeIdAndSize(address, process):
  error = lldb.SBError()
  typeId = process.ReadUnsignedFromMemory(address, 4, error)
  if typeId == LONG_ID or typeId == DOUBLE_ID:
    return (typeId, 8)
  return (typeId, 4)

def dumpFields(p,className,module,process):
  error = lldb.SBError()
  name=module.FindSymbol("Name_" + className)
  nameAddress=name.GetStartAddress().__int__()
  print getString(nameAddress,process)+":"
  debug=module.FindSymbol("Debug_Class_"+className)
  address=debug.GetStartAddress().__int__()
  n=process.ReadUnsignedFromMemory(address, 4, error)
  address+=4
  for i in range(n):
   key=process.ReadUnsignedFromMemory(address, 4, error)
   address+=4
   type=process.ReadUnsignedFromMemory(address, 4, error)
   address+=4
   offset=process.ReadUnsignedFromMemory(address, 4, error)
   address+=4
   print("  "+getString(key,process)+"="+getValue(p,type,offset,process,module))

def getValue(p,typeId,offset,process,module):
  error = lldb.SBError()
  if typeId == LONG_ID or typeId == DOUBLE_ID:
    size=8
  else:
    size=4
  content=process.ReadMemory(p+4+offset*4, size, error)
  buffer=bytearray(content)
  if typeId == INTEGER_ID:
    return str(struct.unpack('i', buffer)[0])
  elif typeId == FLOAT_ID:
    return str(struct.unpack('f', buffer)[0])
  elif typeId == DOUBLE_ID:
    return str(struct.unpack('d', buffer)[0])
  elif typeId == BOOLEAN_ID:
    if struct.unpack('i', buffer)[0] != 0:
      return "true"
    else:
      return "false"
  elif typeId == CHARACTER_ID:
    return "'"+unichr(struct.unpack('Hxx', buffer)[0]).encode('utf-8') + "'"
  elif typeId == CLASS_ID:
    value=struct.unpack('I', buffer)[0]
    # Check for Strings, we know how to display them
    #vectorAddress=process.ReadUnsignedFromMemory(value, 4, error)
    vector=lookup(value,module)
    print(vector)
    if not vector is None and vector.GetName()=="Vector_java_lang_String":
      return getString(value,process)
    else:
      return hex(value)
  else:
    return hex(struct.unpack('I', buffer)[0])
  
def getString(address,process):
  error = lldb.SBError()
  array=process.ReadUnsignedFromMemory(address+4, 4, error)
  return getCharArray(array,process)

def getCharArray(address,process):
  error = lldb.SBError()
  size=process.ReadUnsignedFromMemory(address+4, 4, error)
  content=process.ReadMemory(address+8, size*2, error)
  buffer = bytearray(content)
  template=""
  for i in range(size):
    template=template+"H"
  list=struct.unpack(template,buffer)
  result=u""
  for i in range(size):
    result=result+unichr(list[i])
  return result.encode('utf-8')

def getParameterPack(debug, fp, process):
  error = lldb.SBError()
  p = debug.GetStartAddress().__int__()
  n = process.ReadUnsignedFromMemory(p, 4, error)
  p += 4
  #
  # Skip to end of frame, we have to loop here since we do not now the size of the parameters
  #
  par = fp + 8
  q = p
  for i in range(n):
    (typeId, size) = getTypeIdAndSize(q, process)
    par += size
    q += 4
  #
  # Now collect parameters
  #
  parameters = ""
  for i in range(n):
    (typeId, size) = getTypeIdAndSize(p, process)
    p += 4
    par -= size
    content = process.ReadMemory(par, size, error)
    buffer = bytearray(content)
    if typeId == INTEGER_ID:
      parameters = parameters + str(struct.unpack('i', buffer)[0])
    elif typeId == FLOAT_ID:
      parameters = parameters + str(struct.unpack('f', buffer)[0])
    elif typeId == DOUBLE_ID:
      parameters = parameters + str(struct.unpack('d', buffer)[0])
    elif typeId == BOOLEAN_ID:
      if struct.unpack('i', buffer)[0] != 0:
        parameters = parameters + "true"
      else:
        parameters = parameters + "false"
    elif typeId == CHARACTER_ID:
      parameters = parameters + "'" + unichr(struct.unpack('Hxx', buffer)[0]).encode('utf-8') + "'"
    else:
      parameters = parameters + hex(struct.unpack('I', buffer)[0])
    if i != n - 1:
      parameters = parameters + ","
  return parameters


def cf(debugger, command, result, internal_dict):
  global instance
  instance.cf(debugger, command, result, internal_dict)
  
def do(debugger, command, result, internal_dict):
  global instance
  instance.do(debugger, command, result, internal_dict)


# And the initialization code to add your commands
def __lldb_init_module(debugger, internal_dict):
  debugger.HandleCommand('command script add -f jcc99.cf cf')
  debugger.HandleCommand('command script add -f jcc99.do do')


instance = Jcc99()
