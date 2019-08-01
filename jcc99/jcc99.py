#!/usr/bin/python

import lldb
import commands
import optparse
import shlex
import struct


class Jcc99:

    def __init__(self):
        pass
    
    def cf(self, debugger, command, result, internal_dict):
      target = debugger.GetSelectedTarget()
      process = target.GetProcess()
      thread = process.GetSelectedThread()
      frame = thread.GetSelectedFrame()
      module = frame.GetModule()
#
# Get pc and lookup where we are
#     
      pc = int(frame.FindRegister("pc").value, 0)
      fp = int(frame.FindRegister("ebp").value, 0)
      error = lldb.SBError()
      
      while True:
        here = self.lookup(pc, module)
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
            debug=module.FindSymbol("Debug_"+here.GetName()[7:])
            if debug is None:
              print here.GetName+"+"+str(offset)+"(?)"
            else:
              p=debug.GetStartAddress().__int__()
              n=process.ReadUnsignedFromMemory(p,4,error)
              p+=4
              par=fp+4
              parameters=""
              for i in range(n):
                typeId=process.ReadUnsignedFromMemory(p,4,error)
                p+=4
                if typeId==5 or typeId==8:
                  size=8
                else:
                  size=4
                content=process.ReadMemory(par,size,error)
                buffer=bytearray(content)
                par+=size
                if typeId==7:
                  parameters=parameters+str(struct.unpack('i',buffer)[0])
                else:
                  parameters=parameters+hex(struct.unpack('I',buffer)[0])
                if i!=n-1:
                  parameters=parameters+","
              print "!" + here.GetName() + "+" + str(offset)+"("+parameters+")"
          else:
            print here.GetName() + "+" + str(offset)    
          pc = process.ReadUnsignedFromMemory(fp + 4, 4, error)
          if error.Fail():
            break
          fp = process.ReadUnsignedFromMemory(fp, 4, error)
          if error.Fail():
            break
      
    def lookup(self, address, module):
        for symbol in module:
          if symbol.GetStartAddress().__int__() <= address and symbol.GetEndAddress().__int__() > address:
              return symbol
        return None
            

def cf(debugger, command, result, internal_dict):
    global instance
    instance.cf(debugger, command, result, internal_dict)


# And the initialization code to add your commands
def __lldb_init_module(debugger, internal_dict):
    debugger.HandleCommand('command script add -f jcc99.cf cf')

    
instance = Jcc99()
