#!/usr/bin/python

import lldb
import commands
import optparse
import shlex

def do(debugger, command, result, internal_dict):
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

def ls(debugger, command, result, internal_dict):
    print 'hoi'

# And the initialization code to add your commands
def __lldb_init_module(debugger, internal_dict):
    debugger.HandleCommand('command script add -f jcc99.ls ls')
    debugger.HandleCommand('command script add -f jcc99.do do')
    print 'The "ls" python command has been installed and is ready for use.'
