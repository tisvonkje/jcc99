package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class Invokeinterface extends Instruction
{
  private int methodIndex;
  private int count;
  private byte zero;

  public Invokeinterface(Constant[] constants, int methodIndex, int count, byte zero)
  {
    super(constants);
    this.methodIndex=methodIndex;
    this.count=count;
    this.zero=zero;
  }

}
