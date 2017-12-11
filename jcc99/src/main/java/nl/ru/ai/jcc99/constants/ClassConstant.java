package nl.ru.ai.jcc99.constants;

public class ClassConstant extends Constant
{
  private short nameIndex;

  public ClassConstant(Constant[] constants, short nameIndex)
  {
    super(constants);
    this.nameIndex=nameIndex;
  }
  
  public String toString()
  {
    return String.format("Class #%d // %s",nameIndex,toShortString());
  }
  
  public String toShortString()
  {
    return constants[nameIndex].toShortString();
  }

}
