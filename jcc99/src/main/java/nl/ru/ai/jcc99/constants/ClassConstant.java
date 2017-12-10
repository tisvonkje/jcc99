package nl.ru.ai.jcc99.constants;

public class ClassConstant extends Constant
{
  private short nameIndex;

  public ClassConstant(short nameIndex)
  {
    this.nameIndex=nameIndex;
  }
  
  public String toString()
  {
    return String.format("Class #%d",nameIndex);
  }

}
