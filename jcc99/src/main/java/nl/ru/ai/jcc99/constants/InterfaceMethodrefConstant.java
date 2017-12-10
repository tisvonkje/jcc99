package nl.ru.ai.jcc99.constants;

public class InterfaceMethodrefConstant extends Constant
{
  private short classIndex;
  private short nameAndTypeIndex;

  public InterfaceMethodrefConstant(short classIndex, short nameAndTypeIndex)
  {
    this.classIndex=classIndex;
    this.nameAndTypeIndex=nameAndTypeIndex;
  }
  
  public String toString()
  {
    return String.format("InterfaceMethodref #%d.#%d",classIndex,nameAndTypeIndex);
  }

}
