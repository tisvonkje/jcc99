package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LnegInstruction extends Instruction
{

  public LnegInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "lneg";
  }

}
