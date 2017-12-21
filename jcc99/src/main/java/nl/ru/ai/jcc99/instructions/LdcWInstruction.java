package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LdcWInstruction extends Instruction
{
  private int index;

  public LdcWInstruction(Constant[] constants, int index)
  {
    super(constants);
    this.index=index;
  }

}
