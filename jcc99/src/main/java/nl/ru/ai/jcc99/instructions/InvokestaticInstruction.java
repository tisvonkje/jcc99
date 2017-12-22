package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class InvokestaticInstruction extends Instruction
{
  private int methodIndex;

  public InvokestaticInstruction(Constant[] constants, int methodIndex)
  {
    super(constants);
    this.methodIndex=methodIndex;
  }
  
  public String toString()
  {
    return String.format("invokestatic %d (%s),%d,%d",methodIndex,constants[methodIndex].toShortString());
  }

}
