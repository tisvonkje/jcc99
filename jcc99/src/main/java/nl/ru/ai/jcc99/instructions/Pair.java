package nl.ru.ai.jcc99.instructions;

public class Pair
{
  private int match;
  private int offset;

  public Pair(int match, int offset)
  {
    this.match=match;
    this.offset=offset;
  }
  
  public String toString()
  {
    return String.format("(%d,%d)",match,offset);
  }

}
