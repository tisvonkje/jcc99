package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class IfIcmpInstruction extends Instruction
{
  private Condition condition;
  private short offset;

  public IfIcmpInstruction(Constant[] constants, Condition condition, short offset)
  {
    super(constants);
    this.condition=condition;
    this.offset=offset;
  }

}
