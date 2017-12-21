package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class IpushInstruction extends Instruction
{

  private int value;

  public IpushInstruction(Constant[] constants, int value)
  {
    super(constants);
    this.value=value;
  }

}
