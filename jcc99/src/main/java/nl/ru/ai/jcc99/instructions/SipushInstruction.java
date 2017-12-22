package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class SipushInstruction extends Instruction
{
  private short value;

  public SipushInstruction(Constant[] constants, short value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("sipush %d",value);
  }

}
