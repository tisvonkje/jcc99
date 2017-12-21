package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DstoreInstruction extends Instruction
{
  private int local;

  public DstoreInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }

}
