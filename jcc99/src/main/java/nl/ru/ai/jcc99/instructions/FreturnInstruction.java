package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FreturnInstruction extends Instruction
{

  public FreturnInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "freturn";
  }

}
