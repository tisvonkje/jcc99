package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class AloadInstruction extends Instruction
{
  private int local;

  public AloadInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }

  public String toString()
  {
    return String.format("aload %d",local);
  }

}
