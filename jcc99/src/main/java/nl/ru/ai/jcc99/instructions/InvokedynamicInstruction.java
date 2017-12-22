package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class InvokedynamicInstruction extends Instruction
{

  private int methodIndex;
  private byte zero1;
  private byte zero2;

  public InvokedynamicInstruction(Constant[] constants, int methodIndex, byte zero1, byte zero2)
  {
    super(constants);
    this.methodIndex=methodIndex;
    this.zero1=zero1;
    this.zero2=zero2;
  }
  
  public String toString()
  {
    return String.format("invokedynamic %d (%s),%d,%d",methodIndex,constants[methodIndex].toShortString(),zero1,zero2);
  }

}
