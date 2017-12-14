package nl.ru.ai.jcc99.attributes;

import java.awt.HeadlessException;
import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public abstract class Attribute
{
  protected Constant[] constants;

  public Attribute(Constant[] constants)
  {
    this.constants=constants;
  }

  public static Attribute create(Constant[] constants, ByteBuffer buffer)
  {
    short attributeNameIndex=buffer.getShort();
    int attributeLength=buffer.getInt();
    byte[] info=new byte[attributeLength];
    buffer.get(info);
    ByteBuffer sub=ByteBuffer.wrap(info);
    String attributeName=constants[attributeNameIndex].toShortString();
    if("Code".equals(attributeName))
      return new CodeAtrribute(constants,sub);
    else
      return new CustomAttribute(constants,attributeName,sub);
  }
}
