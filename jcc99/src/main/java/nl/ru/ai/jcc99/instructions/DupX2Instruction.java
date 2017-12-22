package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DupX2Instruction extends Instruction
{

  public DupX2Instruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dup_x2";
  }

}
