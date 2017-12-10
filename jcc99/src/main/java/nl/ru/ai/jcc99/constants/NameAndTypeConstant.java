package nl.ru.ai.jcc99.constants;

public class NameAndTypeConstant extends Constant
{
  private short nameIndex;
  private short descriptorIndex;

  public NameAndTypeConstant(short nameIndex, short descriptorIndex)
  {
    this.nameIndex=nameIndex;
    this.descriptorIndex=descriptorIndex;
  }
  
  public String toString()
  {
    return String.format("NameAndType #%d:#%d",nameIndex,descriptorIndex);
  }

}
