package nl.ru.ai.jcc99.constants;

import nl.ru.ai.jcc99.Coder;

public class IntegerConstant extends InlineConstant
{
  private int value;

  public IntegerConstant(Constant[] constants, int value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Integer %s",toShortString());
  }

  @Override
  public String toShortString()
  {
    return String.format("%d",value);
  }
  
  public void codeLoad(Coder coder)
  {
    coder.codePushInt(value);
  }

}
