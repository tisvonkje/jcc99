package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DupInstruction extends Instruction
{

  public DupInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dup";
  }

}
