package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class IstoreInstruction extends Instruction
{
  private int local;

  public IstoreInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }

}
