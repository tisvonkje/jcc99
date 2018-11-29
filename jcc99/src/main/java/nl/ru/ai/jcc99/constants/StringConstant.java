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
    coder.codeWord("_Vector_java_lang_String");
    coder.codeWord(".+4");
    coder.codeWord(0); //FIXME: classvector for char []
    String string=constants[stringIndex].toShortString();
    coder.codeWord(string.length());
    for(int i=0;i<string.length();i++)
      coder.codeChar(string.charAt(i));
  }

}
