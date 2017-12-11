package nl.ru.ai.jcc99.constants;

public class MethodrefConstant extends Constant
{
  private short classIndex;
  private short nameAndTypeIndex;

  public MethodrefConstant(Constant[] constants, short classIndex, short nameAndTypeIndex)
  {
    super(constants);
    this.classIndex=classIndex;
    this.nameAndTypeIndex=nameAndTypeIndex;
  }
  
  public String toString()
  {
    return String.format("Methodref #%d.#%d",classIndex,nameAndTypeIndex);
  }

}
