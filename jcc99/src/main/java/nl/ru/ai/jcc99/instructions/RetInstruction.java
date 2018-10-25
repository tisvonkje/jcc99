package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class RetInstruction extends Instruction
{
  @SuppressWarnings("unused")
  private int local;

  public RetInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }

}
