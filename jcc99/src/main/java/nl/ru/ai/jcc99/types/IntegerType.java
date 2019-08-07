package nl.ru.ai.jcc99.types;

public class IntegerType implements Type
{
  public String toString()
  {
    return "int";
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
    return INTEGER_ID;
  }
}
