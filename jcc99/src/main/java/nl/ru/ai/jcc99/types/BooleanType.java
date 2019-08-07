package nl.ru.ai.jcc99.types;

public class BooleanType implements Type
{
  public String toString()
  {
    return "boolean";
  }
  
  public int unitSize()
  {
    return 1;
  }

  public int parameterUnitSize()
  {
    throw new RuntimeException("Illegal type");
  }

  public int getDebugId()
  {
    return BOOLEAN_ID;
  }
}
