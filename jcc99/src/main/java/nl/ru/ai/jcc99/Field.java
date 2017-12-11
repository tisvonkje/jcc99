package nl.ru.ai.jcc99;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public class Field
{
  private short accessFlags;
  private short nameIndex;
  private short descriptorIndex;
  private Attribute[] attributes;
  private Constant[] constants;

  public Field(Constant [] constants, ByteBuffer buffer)
  {
    this.constants=constants;
    accessFlags=buffer.getShort();
    nameIndex=buffer.getShort();
    descriptorIndex=buffer.getShort();
    short attributeCount=buffer.getShort();
    attributes=new Attribute[attributeCount];
    for(int i=0;i<attributeCount;i++)
      attributes[i]=new Attribute(constants,buffer);
  }
  
  public String toString()
  {
    StringBuffer attr=new StringBuffer();
    for(int i=0;i<attributes.length;i++)
    {
      attr.append(attributes[i]);
      attr.append(' ');
    }
    return String.format("flags=%04x, name=%s, descriptor=%s attributes=%s\n",accessFlags,constants[nameIndex].toShortString(),constants[descriptorIndex].toShortString(),new String(attr));
  }

}
