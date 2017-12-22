package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DnegInstruction extends Instruction
{

  public DnegInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dneg";
  }

}
