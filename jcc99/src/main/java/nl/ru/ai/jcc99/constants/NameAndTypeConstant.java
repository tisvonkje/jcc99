package nl.ru.ai.jcc99.constants;

public class NameAndTypeConstant extends Constant
{
  private short nameIndex;
  private short descriptorIndex;

  public NameAndTypeConstant(Constant[] constants, short nameIndex, short descriptorIndex)
  {
    super(constants);
    this.nameIndex=nameIndex;
    this.descriptorIndex=descriptorIndex;
  }
  
  public String toString()
  {
    return String.format("NameAndType #%d:#%d // %s",nameIndex,descriptorIndex,toShortString());
  }

  @Override
  String toShortString()
  {
    return String.format("%s:%s",constants[nameIndex].toShortString(),constants[descriptorIndex].toShortString());
  }

}
