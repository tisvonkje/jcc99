package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class SastoreInstruction extends Instruction
{

  public SastoreInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "sastore";
  }
}
