package nl.ru.ai.jcc99.constants;

import nl.ru.ai.jcc99.Coder;

public class FloatConstant extends InlineConstant
{
  private float value;

  public FloatConstant(Constant[] constants, float value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Float %s",toShortString());
  }

  @Override
  public String toShortString()
  {
    return String.format("%f",value);
  }
  
  public void codeLoad(Coder coder)
  {
    coder.codePushFloat(value);
  }

}
