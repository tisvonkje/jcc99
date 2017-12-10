package nl.ru.ai.jcc99.constants;

public class InvokeDynamicConstant extends Constant
{
  private short bootstrapMethodAttrIndex;
  private short nameAndTypeIndex;

  public InvokeDynamicConstant(short bootstrapMethodAttrIndex, short nameAndTypeIndex)
  {
    this.bootstrapMethodAttrIndex=bootstrapMethodAttrIndex;
    this.nameAndTypeIndex=nameAndTypeIndex;
  }
  
  public String toString()
  {
    return String.format("InvokeDynamic #%d #%d",bootstrapMethodAttrIndex,nameAndTypeIndex);
  }
}
