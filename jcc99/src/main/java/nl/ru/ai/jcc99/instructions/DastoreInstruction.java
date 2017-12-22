package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DastoreInstruction extends Instruction
{

  public DastoreInstruction(Constant[] constants)
  {
     super(constants);
  }
  
  public String toString()
  {
    return "dastore";
  }
}
