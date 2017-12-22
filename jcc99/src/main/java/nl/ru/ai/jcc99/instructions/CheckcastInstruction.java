package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class CheckcastInstruction extends Instruction
{
  private int classIndex;

  public CheckcastInstruction(Constant[] constants, int classIndex)
  {
    super(constants);
    this.classIndex=classIndex;
  }
  
  public String toString()
  {
    return String.format("checkcast %d (%s)",classIndex,constants[classIndex].toShortString());
  }

}
