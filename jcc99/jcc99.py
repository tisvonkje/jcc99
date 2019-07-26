#!/usr/bin/python

import lldb
import commands
import optparse
import shlex

class Jcc99:
    def __init__(self):
        pass
    
    def cf(self, debugger, command, result, internal_dict):
      target=debugger.GetSelectedTarget()
      process=target.GetProcess()
      thread=process.GetSelectedThread()
      frame=thread.GetSelectedFrame()
      module=frame.GetModule()
#
# Get pc and lookup where we are
#     
      pc=int(frame.FindRegister("pc").value,0)
      fp=int(frame.FindRegister("ebp").value,0)
      error=lldb.SBError()
      
      while True:
        here=self.lookup(pc,module)
        if here is None:
          break
        else:
          offset=pc-here.GetStartAddress().__int__()
          print here.GetName()+"+"+str(offset)
          pc=process.ReadUnsignedFromMemory(fp+4,4,error)
          if error.Fail():
            break
          fp=process.ReadUnsignedFromMemory(fp,4,error)
          if error.Fail():
            break
      
    def lookup(self, address, module):
        for symbol in module:
          if symbol.GetStartAddress().__int__()<= address and symbol.GetEndAddress().__int__()> address:
              return symbol
        return None
            

def cf(debugger, command, result, internal_dict):
    global instance
    instance.cf(debugger,command,result,internal_dict)

# And the initialization code to add your commands
def __lldb_init_module(debugger, internal_dict):
    debugger.HandleCommand('command script add -f jcc99.cf cf')
    
instance=Jcc99()
