package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FnegInstruction extends Instruction
{

  public FnegInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "fneg";
  }

}
