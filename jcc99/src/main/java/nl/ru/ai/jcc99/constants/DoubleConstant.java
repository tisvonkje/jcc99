package nl.ru.ai.jcc99.constants;

import nl.ru.ai.jcc99.ClassLoader;

public class DoubleConstant extends InlineConstant
{
  private double value;

  public DoubleConstant(Constant[] constants, double value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Double %s\n",toShortString());
  }
  
  public String toShortString()
  {
    return String.format("%f",value);
  }

}
