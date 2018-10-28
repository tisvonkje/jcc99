package nl.ru.ai.jcc99.types;

public class CharacterType implements Type
{
  public String toString()
  {
    return "char";
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
