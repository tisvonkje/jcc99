package nl.ru.ai.jcc99.constants;

public class MethodrefConstant extends InlineConstant
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
    return String.format("Methodref #%d.#%d // %s",classIndex,nameAndTypeIndex,toShortString());
  }

  @Override
  public String toShortString()
  {
    return String.format("%s.%s",constants[classIndex].toShortString(),constants[nameAndTypeIndex].toShortString());
  }
  
  public short getNameAndTypeIndex()
  {
    return nameAndTypeIndex;
  }
  
  public short getClassIndex()
  {
    return classIndex;
  }
  
}
