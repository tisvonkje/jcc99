package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class Ldc2WInstruction extends Instruction
{
  private int index;

  public Ldc2WInstruction(Constant[] constants, int index)
  {
    super(constants);
    this.index=index;
  }

}
