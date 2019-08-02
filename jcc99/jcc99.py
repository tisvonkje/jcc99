#!/usr/bin/python

import lldb
import commands
import optparse
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
          debug = module.FindSymbol("Debug_" + here.GetName()[7:])
          if debug is None:
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


def getParameterPack(debug, fp, process):
  error = lldb.SBError()
  p = debug.GetStartAddress().__int__()
  n = process.ReadUnsignedFromMemory(p, 4, error)
  p += 4
  #
  # Skip to end of frame, we have to loop here since we do not now the size of the parameters
  #
  par = fp + 4
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
    content = process.ReadMemory(par, size, error)
    buffer = bytearray(content)
    par -= size
    if typeId == INTEGER_ID:
      parameters = parameters + str(struct.unpack('i', buffer)[0])
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


# And the initialization code to add your commands
def __lldb_init_module(debugger, internal_dict):
  debugger.HandleCommand('command script add -f jcc99.cf cf')


instance = Jcc99()
