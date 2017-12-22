package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class PutstaticInstruction extends Instruction
{
  private int fieldIndex;

  public PutstaticInstruction(Constant[] constants, int fieldIndex)
  {
    super(constants);
    this.fieldIndex=fieldIndex;
  }
  
  public String toString()
  {
    return String.format("putstatic %d (%s)",fieldIndex,constants[fieldIndex].toShortString());
  }

}
