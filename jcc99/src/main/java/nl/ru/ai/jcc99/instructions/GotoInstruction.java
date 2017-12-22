package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class GotoInstruction extends Instruction
{
  private int offset;

  public GotoInstruction(Constant[] constants, int offset)
  {
    super(constants);
    this.offset=offset;
  }
  
  public String toString()
  {
    return String.format("goto %d",offset);
  }

}
