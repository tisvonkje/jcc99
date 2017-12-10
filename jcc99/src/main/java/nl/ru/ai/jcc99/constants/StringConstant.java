package nl.ru.ai.jcc99.constants;

public class StringConstant extends Constant
{
  private short stringIndex;

  public StringConstant(short stringIndex)
  {
    this.stringIndex=stringIndex;
  }
  
  public String toString()
  {
    return String.format("String #%d",stringIndex);
  }

}
