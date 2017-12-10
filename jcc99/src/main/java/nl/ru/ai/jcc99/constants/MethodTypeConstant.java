package nl.ru.ai.jcc99.constants;

public class MethodTypeConstant extends Constant
{
  private short descriptorIndex;

  public MethodTypeConstant(short descriptorIndex)
  {
    this.descriptorIndex=descriptorIndex;
  }
  
  public String toString()
  {
    return String.format("MethodType #%d",descriptorIndex);
  }

}
