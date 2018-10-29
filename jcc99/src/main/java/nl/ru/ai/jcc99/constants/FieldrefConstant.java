package nl.ru.ai.jcc99.constants;

import nl.ru.ai.jcc99.ClassLoader;

public class FieldrefConstant extends InlineConstant
{
  private short classIndex;
  private short nameAndTypeIndex;

  public FieldrefConstant(Constant[] constants, short classIndex, short nameAndTypeIndex)
  {
    super(constants);
    this.classIndex=classIndex;
    this.nameAndTypeIndex=nameAndTypeIndex;
  }
  
  public String toString()
  {
    return String.format("Fieldref #%d:#%d // %s",classIndex,nameAndTypeIndex,toShortString());
  }

  @Override
  public String toShortString()
  {
    return String.format("%s:%s",constants[classIndex].toShortString(),constants[nameAndTypeIndex].toShortString());
  }

  public String getClassName()
  {
    return constants[classIndex].toShortString();
  }
}
