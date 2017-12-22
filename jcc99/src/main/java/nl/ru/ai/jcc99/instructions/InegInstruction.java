package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class InegInstruction extends Instruction
{

  public InegInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "ineg";
  }

}
