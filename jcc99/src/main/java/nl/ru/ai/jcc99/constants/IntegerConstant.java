package nl.ru.ai.jcc99.constants;

public class IntegerConstant extends Constant
{
  private int value;

  public IntegerConstant(int value)
  {
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Integer '%d'",value);
  }

}
