package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.constants.Constant;

public class IconstInstruction extends Instruction
{
  private int value;

  public IconstInstruction(Constant[] constants, int value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("iconst %d",value);
  }
  
  public void code(int parameterUnits, Coder coder)
  {
    coder.codePushInt(value);
  }

}
