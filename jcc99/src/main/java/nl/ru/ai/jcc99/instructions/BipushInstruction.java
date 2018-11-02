package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.constants.Constant;

public class BipushInstruction extends Instruction
{
  private int value;

  public BipushInstruction(Constant[] constants, int value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("bipush %d",value);
  }
  
  public void code(int parameterUnits, Coder coder)
  {
    coder.codePushByte(value);
  }
}
