package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class AnewarrayInstruction extends Instruction
{
  private int componentTypeIndex;

  public AnewarrayInstruction(Constant[] constants, int componentTypeIndex)
  {
    super(constants);
    this.componentTypeIndex=componentTypeIndex;
  }
  
  public String toString()
  {
    return String.format("anewarray %d (%s)",componentTypeIndex,constants[componentTypeIndex].toShortString());
  }

}
