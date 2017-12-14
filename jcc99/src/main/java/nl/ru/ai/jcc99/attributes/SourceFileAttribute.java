package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public class SourceFileAttribute extends Attribute
{
  private short nameIndex;

  public SourceFileAttribute(Constant[] constants, ByteBuffer buffer)
  {
    super(constants);
    nameIndex=buffer.getShort();
  }
  
  public String toString()
  {
    return String.format("SoureFile(%s)",constants[nameIndex].toShortString());
  }

}
