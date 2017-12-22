package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FaloadInstruction extends Instruction
{

  public FaloadInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "faload";
  }

}
