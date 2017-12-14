package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public class CustomAttribute extends Attribute
{
  private String attributeName;
  private byte[] info;

  public CustomAttribute(Constant[] constants, String attributeName, ByteBuffer sub)
  {
    super(constants);
    this.attributeName=attributeName;
    info=sub.array();
  }
  
  public String toString()
  {
    StringBuffer buffer=new StringBuffer();
    for(int i=0;i<info.length;i++)
      buffer.append(String.format("%02x",info[i]));
    return String.format("%s(%s)",attributeName,new String(buffer));
  }

}
