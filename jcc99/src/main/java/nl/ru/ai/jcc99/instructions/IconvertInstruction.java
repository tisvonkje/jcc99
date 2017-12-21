package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class IconvertInstruction extends Instruction
{
  private Conversion conversion;

  public IconvertInstruction(Constant[] constants, Conversion conversion)
  {
    super(constants);
    this.conversion=conversion;
  }

}
