package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class InvokeinterfaceInstruction extends Instruction
{
  private int methodIndex;
  private int count;
  private byte zero;

  public InvokeinterfaceInstruction(Constant[] constants, int methodIndex, int count, byte zero)
  {
    super(constants);
    this.methodIndex=methodIndex;
    this.count=count;
    this.zero=zero;
  }
  
  public String toString()
  {
    return String.format("invokedynamic %d (%s),%d,%d",methodIndex,constants[methodIndex].toShortString(),count,zero);
  }

}
