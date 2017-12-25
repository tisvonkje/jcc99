package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public class LocalVariable
{
  private Constant[] constants;
  private int startPc;
  private int length;
  private int nameIndex;
  private int descriptorIndex;
  private int index;

  public LocalVariable(Constant [] constants,ByteBuffer buffer)
  {
    this.constants=constants;
    startPc=buffer.getShort()&0xfffff;
    length=buffer.getShort()&0xffff;
    nameIndex=buffer.getShort()&0xffff;
    descriptorIndex=buffer.getShort()&0xffff;
    index=buffer.getShort()&0xffff;
  }
  
  public String toString()
  {
    return String.format("<%d,%d>,%d (%s),%d (%s),%d]",startPc,startPc+length,nameIndex,constants[nameIndex].toShortString(),descriptorIndex,constants[descriptorIndex].toShortString(),index);
  }

}
