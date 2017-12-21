package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class InstanceofInstruction extends Instruction
{
  private int classIndex;

  public InstanceofInstruction(Constant[] constants, int classIndex)
  {
    super(constants);
    this.classIndex=classIndex;
  }

}
