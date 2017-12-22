package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DcmpgInstruction extends Instruction
{

  public DcmpgInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dcmpg";
  }

}
