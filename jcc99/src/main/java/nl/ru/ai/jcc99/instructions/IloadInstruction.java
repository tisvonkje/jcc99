package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class IloadInstruction extends Instruction
{
  private int local;

  public IloadInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }

}
