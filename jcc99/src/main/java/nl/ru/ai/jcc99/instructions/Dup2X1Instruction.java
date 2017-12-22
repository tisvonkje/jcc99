package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class Dup2X1Instruction extends Instruction
{

  public Dup2X1Instruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dup2_x1";
  }

}
