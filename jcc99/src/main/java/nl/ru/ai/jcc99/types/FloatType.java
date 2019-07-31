package nl.ru.ai.jcc99.types;

public class FloatType implements Type
{
  public String toString()
  {
    return "float";
  }
  
  public int unitSize()
  {
    return 1;
  }

  public int parameterUnitSize()
  {
    throw new RuntimeException("Illegal type");
  }

  public int codeDebugId()
  {
    return FLOAT_ID;
  }

}
