package nl.ru.ai.jcc99.constants;

public class InterfaceMethodrefConstant extends Constant
{
  private short classIndex;
  private short nameAndTypeIndex;

  public InterfaceMethodrefConstant(Constant[] constants, short classIndex, short nameAndTypeIndex)
  {
    super(constants);
    this.classIndex=classIndex;
    this.nameAndTypeIndex=nameAndTypeIndex;
  }
  
  public String toString()
  {
    return String.format("InterfaceMethodref #%d.#%d // %s",classIndex,nameAndTypeIndex,toShortString());
  }

  @Override
  public String toShortString()
  {
    return String.format("%s.%s",constants[classIndex].toShortString(),constants[nameAndTypeIndex].toShortString());
  }

}
