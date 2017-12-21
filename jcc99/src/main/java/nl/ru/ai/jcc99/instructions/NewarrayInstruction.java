package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class NewarrayInstruction extends Instruction
{
  private byte type;

  public NewarrayInstruction(Constant[] constants, byte type)
  {
    super(constants);
    this.type=type;
  }

}
