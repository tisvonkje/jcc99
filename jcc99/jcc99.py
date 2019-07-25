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

      print frame.FindRegister("esp")
      
      pc=int(frame.FindRegister("pc").value,0)
      print self.lookup(pc,frame.GetModule())
      
      error = lldb.SBError()
      uint = process.ReadUnsignedFromMemory(pc, 1, error)
      if error.Success():
        print('integer: %u' % uint)
      else:
        print('error: ', error)
      print frame.FindRegister("eax")
      
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
