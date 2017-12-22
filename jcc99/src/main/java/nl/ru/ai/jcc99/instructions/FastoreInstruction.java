package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FastoreInstruction extends Instruction
{

  public FastoreInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "fastore";
  }

}
