package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LreturnInstruction extends Instruction
{

  public LreturnInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "lreturn";
  }

}
