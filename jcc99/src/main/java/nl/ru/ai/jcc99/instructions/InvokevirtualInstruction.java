package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class InvokevirtualInstruction extends Instruction
{
  private int methodIndex;

  public InvokevirtualInstruction(Constant[] constants, int methodIndex)
  {
    super(constants);
    this.methodIndex=methodIndex;
  }

}
