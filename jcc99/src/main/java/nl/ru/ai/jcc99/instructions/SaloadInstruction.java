package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class SaloadInstruction extends Instruction
{

  public SaloadInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "saload";
  }

}
