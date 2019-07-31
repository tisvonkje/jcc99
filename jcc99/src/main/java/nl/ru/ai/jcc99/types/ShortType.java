package nl.ru.ai.jcc99.types;

public class ShortType implements Type
{
  public String toString()
  {
    return "short";
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
    return SHORT_ID;
  }

}
