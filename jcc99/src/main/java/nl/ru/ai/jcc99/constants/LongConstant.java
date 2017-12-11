package nl.ru.ai.jcc99.constants;

public class LongConstant extends Constant
{
  private long value;

  public LongConstant(Constant[] constants, long value)
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
