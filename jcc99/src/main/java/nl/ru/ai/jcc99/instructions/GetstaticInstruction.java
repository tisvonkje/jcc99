package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class GetstaticInstruction extends Instruction
{
  private int fieldIndex;

  public GetstaticInstruction(Constant[] constants, int fieldIndex)
  {
    super(constants);
    this.fieldIndex=fieldIndex;
  }
  
  public String toString()
  {
    return String.format("getstatic %d (%s)",fieldIndex,constants[fieldIndex].toShortString());
  }

}
