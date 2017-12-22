package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DreturnInstruction extends Instruction
{

  public DreturnInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dreturn";
  }

}
