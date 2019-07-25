#!/usr/bin/python

import lldb
import commands
import optparse
import shlex

class Jcc99:
    def __init__(self):
        pass
    
    def do(self, debugger, command, result, internal_dict):
      target=debugger.GetSelectedTarget()
      process=target.GetProcess()
      thread=process.GetSelectedThread()
      frame=thread.GetSelectedFrame()
      for symbol in frame.GetModule():
          print symbol.GetName()
      print frame.FindRegister("esp")
      pc=int(frame.FindRegister("pc").value,0)
      error = lldb.SBError()
      uint = process.ReadUnsignedFromMemory(pc, 1, error)
      if error.Success():
        print('integer: %u' % uint)
      else:
        print('error: ', error)
      print frame.FindRegister("eax")    

def do(debugger, command, result, internal_dict):
    global instance
    instance.do(debugger,command,result,internal_dict)

# And the initialization code to add your commands
def __lldb_init_module(debugger, internal_dict):
    debugger.HandleCommand('command script add -f jcc99.do do')
    
instance=Jcc99()
