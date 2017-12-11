package nl.ru.ai.jcc99.constants;

public class DoubleConstant extends Constant
{
  private double value;

  public DoubleConstant(Constant[] constants, double value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Double '%f'",value);
  }

}
