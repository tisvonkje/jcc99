package nl.ru.ai.jcc99.types;

public class LongType implements Type
{
  public String toString()
  {
    return "long";
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
