package nl.ru.ai.jcc99.constants;

public class FieldrefConstant extends Constant
{
  private short classIndex;
  private short nameAndTypeIndex;

  public FieldrefConstant(short classIndex, short nameAndTypeIndex)
  {
    this.classIndex=classIndex;
    this.nameAndTypeIndex=nameAndTypeIndex;
  }
  
  public String toString()
  {
    return String.format("Fieldref #%d:#%d",classIndex,nameAndTypeIndex);
  }

}
