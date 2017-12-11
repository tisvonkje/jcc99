package nl.ru.ai.jcc99.constants;

public class IntegerConstant extends Constant
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

}
