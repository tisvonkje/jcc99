package nl.ru.ai.jcc99;

import java.awt.HeadlessException;
import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public class Attribute
{
  private Constant[] constants;
  private short attributeNameIndex;
  private byte[] info;

  public Attribute(Constant [] constants, ByteBuffer buffer)
  {
    this.constants=constants;
    attributeNameIndex=buffer.getShort();
    int attributeLength=buffer.getInt();
    info=new byte[attributeLength];
    buffer.get(info);
  }
  
  public String toString()
  {
    StringBuffer buffer=new StringBuffer();
    for(int i=0;i<info.length;i++)
      buffer.append(String.format("%02x",info[i]));
    return String.format("%s(%s)",constants[attributeNameIndex].toShortString(),new String(buffer));
  }

}
