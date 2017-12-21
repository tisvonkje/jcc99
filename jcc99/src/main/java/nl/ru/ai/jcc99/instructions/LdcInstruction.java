package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LdcInstruction extends Instruction
{
  private int index;

  public LdcInstruction(Constant[] constants, int index)
  {
    super(constants);
    this.index=index;
  }

}
