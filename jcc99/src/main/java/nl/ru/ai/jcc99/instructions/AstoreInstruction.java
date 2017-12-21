package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class AstoreInstruction extends Instruction
{
  private int value;

  public AstoreInstruction(Constant[] constants, int value)
  {
    super(constants);
    this.value=value;
  }

}
