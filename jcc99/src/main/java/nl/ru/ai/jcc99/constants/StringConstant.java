package nl.ru.ai.jcc99.constants;

public class StringConstant extends Constant
{
  private short stringIndex;

  public StringConstant(Constant[] constants, short stringIndex)
  {
    super(constants);
    this.stringIndex=stringIndex;
  }
  
  public String toString()
  {
    return String.format("String #%d // %s",stringIndex,toShortString());
  }

  @Override
  String toShortString()
  {
    return constants[stringIndex].toShortString();
  }

}
