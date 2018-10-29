package nl.ru.ai.jcc99.constants;

public class MethodTypeConstant extends InlineConstant
{
  private short descriptorIndex;

  public MethodTypeConstant(Constant[] constants, short descriptorIndex)
  {
    super(constants);
    this.descriptorIndex=descriptorIndex;
  }
  
  public String toString()
  {
    return String.format("MethodType #%d // %s",descriptorIndex,toShortString());
  }

  @Override
  public String toShortString()
  {
    return constants[descriptorIndex].toShortString();
  }

}
