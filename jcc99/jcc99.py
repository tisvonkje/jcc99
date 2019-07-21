#!/usr/bin/python

import lldb
import commands
import optparse
import shlex

def do(debugger, command, result, internal_dict):
    print 'dump object'

def ls(debugger, command, result, internal_dict):
    print 'hoi'

# And the initialization code to add your commands
def __lldb_init_module(debugger, internal_dict):
    debugger.HandleCommand('command script add -f jcc99.ls ls')
    debugger.HandleCommand('command script add -f jcc99.do do')
    print 'The "ls" python command has been installed and is ready for use.'
