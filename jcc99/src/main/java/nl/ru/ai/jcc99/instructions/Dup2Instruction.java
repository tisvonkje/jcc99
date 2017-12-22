package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class Dup2Instruction extends Instruction
{

  public Dup2Instruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dup2";
  }

}
