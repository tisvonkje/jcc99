package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class InvokespecialInstruction extends Instruction
{
  private int methodIndex;

  public InvokespecialInstruction(Constant[] constants, int methodIndex)
  {
    super(constants);
    this.methodIndex=methodIndex;
  }
  
  public String toString()
  {
    return String.format("invokespecial %d (%s)",methodIndex,constants[methodIndex].toShortString());
  }

}
