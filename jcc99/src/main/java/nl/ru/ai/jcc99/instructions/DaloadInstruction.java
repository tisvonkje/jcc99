package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DaloadInstruction extends Instruction
{

  public DaloadInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "daload";
  }

}
