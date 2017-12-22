package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class Pop2Instruction extends Instruction
{

  public Pop2Instruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "pop2";
  }

}
