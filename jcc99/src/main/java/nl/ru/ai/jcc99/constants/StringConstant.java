package nl.ru.ai.jcc99.constants;

import nl.ru.ai.jcc99.Coder;

public class StringConstant extends OutlineConstant
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
  public String toShortString()
  {
    return constants[stringIndex].toShortString();
  }
  
  public void code(Coder coder)
  {
    super.code(coder);
    coder.codeAsciz(constants[stringIndex].toShortString());
  }

}
