package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class GotoInstruction extends Instruction
{
  private short offset;

  public GotoInstruction(Constant[] constants, short offset)
  {
    super(constants);
    this.offset=offset;
  }

}
