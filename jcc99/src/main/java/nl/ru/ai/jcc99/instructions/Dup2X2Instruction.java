package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class Dup2X2Instruction extends Instruction
{

  public Dup2X2Instruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dup2_x2";
  }

}
