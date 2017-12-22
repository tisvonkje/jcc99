package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LastoreInstruction extends Instruction
{

  public LastoreInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "lastore";
  }

}
