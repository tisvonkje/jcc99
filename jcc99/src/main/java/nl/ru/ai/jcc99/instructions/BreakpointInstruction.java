package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class BreakpointInstruction extends Instruction
{

  public BreakpointInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "breakpoint";
  }

}
