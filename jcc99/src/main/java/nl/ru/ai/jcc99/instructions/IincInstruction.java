package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class IincInstruction extends Instruction
{
  private int local;
  private int value;

  public IincInstruction(Constant[] constants, int local, int value)
  {
    super(constants);
    this.local=local;
    this.value=value;
  }

}
