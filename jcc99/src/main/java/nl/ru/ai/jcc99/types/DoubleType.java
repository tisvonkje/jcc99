package nl.ru.ai.jcc99.types;

public class DoubleType implements Type
{
  public String toString()
  {
    return "double";
  }
  
  public int unitSize()
  {
    return 2;
  }

  public int parameterUnitSize()
  {
    throw new RuntimeException("Illegal type");
  }

}
