package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FcmpgInstruction extends Instruction
{

  public FcmpgInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "fcmpg";
  }

}
