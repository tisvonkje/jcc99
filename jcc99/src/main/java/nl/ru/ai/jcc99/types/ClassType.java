package nl.ru.ai.jcc99.types;

public class ClassType implements Type
{
  private String name;

  public ClassType(String name)
  {
    this.name=name;
  }
  
  public String toString()
  {
    return "Class "+name;
  }
  
  public int unitSize()
  {
    return 1;
  }

  public int parameterUnitSize()
  {
    throw new RuntimeException("Illegal type");
  }

  public String getName()
  {
    return name;
  }

}
