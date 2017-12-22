package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LaloadInstruction extends Instruction
{

  public LaloadInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "laload";
  }

}
