package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FloadInstruction extends Instruction
{
  private int local;

  public FloadInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }

}
