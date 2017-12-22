package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class BastoreInstruction extends Instruction
{

  public BastoreInstruction(Constant[] constants)
  {
    super(constants);
  } 
  
  public String toString()
  {
    return "bastore";
  }
}
