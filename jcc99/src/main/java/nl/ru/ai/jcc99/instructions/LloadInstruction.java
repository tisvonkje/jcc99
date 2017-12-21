package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LloadInstruction extends Instruction
{
  private int local;

  public LloadInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }

}
