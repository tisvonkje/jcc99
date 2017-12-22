package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class CaloadInstruction extends Instruction
{

  public CaloadInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "caload";
  }

}
