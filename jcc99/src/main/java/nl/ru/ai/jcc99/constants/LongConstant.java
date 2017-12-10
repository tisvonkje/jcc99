package nl.ru.ai.jcc99.constants;

public class LongConstant extends Constant
{
  private long value;

  public LongConstant(long value)
  {
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Long '%d'",value);
  }

}
