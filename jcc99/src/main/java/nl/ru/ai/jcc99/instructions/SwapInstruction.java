package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class SwapInstruction extends Instruction
{

  public SwapInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "swap";
  }

}
