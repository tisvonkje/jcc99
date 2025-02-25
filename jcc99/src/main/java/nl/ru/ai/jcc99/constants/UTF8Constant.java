package nl.ru.ai.jcc99.constants;

public class UTF8Constant extends InlineConstant
{
  private byte[] bytes;

  public UTF8Constant(Constant[] constants, byte[] bytes)
  {
    super(constants);
    this.bytes=bytes;
  }
  
  public String toString()
  {
    return String.format("UTF8 %s",new String(bytes));
  }

  @Override
  public String toShortString()
  {
    return new String(bytes);
  }

}
