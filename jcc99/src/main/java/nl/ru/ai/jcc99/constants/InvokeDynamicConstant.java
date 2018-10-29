package nl.ru.ai.jcc99.constants;

public class InvokeDynamicConstant extends InlineConstant
{
  private short bootstrapMethodAttrIndex;
  private short nameAndTypeIndex;

  public InvokeDynamicConstant(Constant[] constants, short bootstrapMethodAttrIndex, short nameAndTypeIndex)
  {
    super(constants);
    this.bootstrapMethodAttrIndex=bootstrapMethodAttrIndex;
    this.nameAndTypeIndex=nameAndTypeIndex;
  }
  
  public String toString()
  {
    return String.format("InvokeDynamic #%d #%d // %s",bootstrapMethodAttrIndex,nameAndTypeIndex,toShortString());
  }

  @Override
  public String toShortString()
  {
    return String.format("%s %s",constants[bootstrapMethodAttrIndex].toShortString(),constants[nameAndTypeIndex].toShortString());
  }
}
