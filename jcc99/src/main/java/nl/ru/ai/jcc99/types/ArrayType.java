package nl.ru.ai.jcc99.types;

public class ArrayType implements Type
{
  private Type elementType;

  public ArrayType(Type elementType)
  {
    this.elementType=elementType;
  }
  
  public String toString()
  {
    return "Array of "+elementType;
  }

  public int unitSize()
  {
    return 1;
  }

  public int parameterUnitSize()
  {
    throw new RuntimeException("Illegal type");
  }

}
