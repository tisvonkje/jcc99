package nl.ru.ai.jcc99.types;

public class VoidType implements Type
{
  public String toString()
  {
    return "void";
  }

  public int unitSize()
  {
    throw new RuntimeException("Illegal type");
  }

  public int parameterUnitSize()
  {
    throw new RuntimeException("Illegal type");
  }
}
