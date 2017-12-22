package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class ConvertInstruction extends Instruction
{
  private Conversion conversion;

  public ConvertInstruction(Constant[] constants, Conversion conversion)
  {
    super(constants);
    this.conversion=conversion;
  }
  
  public String toString()
  {
    return conversion.toString().toLowerCase();
  }

}
