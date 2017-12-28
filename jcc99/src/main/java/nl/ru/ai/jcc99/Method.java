package nl.ru.ai.jcc99;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.attributes.Attribute;
import nl.ru.ai.jcc99.attributes.CodeAtrribute;
import nl.ru.ai.jcc99.constants.Constant;

public class Method
{
  private Constant[] constants;
  private short accessFlags;
  private short nameIndex;
  private short descriptorIndex;
  private Attribute[] attributes;
  private boolean markedForCoding;

  public Method(Constant[] constants, ByteBuffer buffer)
  {
    this.constants=constants;
    accessFlags=buffer.getShort();
    nameIndex=buffer.getShort();
    descriptorIndex=buffer.getShort();
    short attributeCount=buffer.getShort();
    attributes=new Attribute[attributeCount];
    for(int i=0;i<attributeCount;i++)
      attributes[i]=Attribute.create(constants,buffer);
    markedForCoding=false;
  }
  
  public String toString()
  {
    StringBuffer attr=new StringBuffer();
    for(int i=0;i<attributes.length;i++)
    {
      attr.append(attributes[i]);
      attr.append(' ');
    }
    return String.format("flags=%04x, name=%s, descriptor=%s attributes=%s",accessFlags,constants[nameIndex].toShortString(),constants[descriptorIndex].toShortString(),new String(attr));
  }

  public String getDescriptor()
  {
    return constants[descriptorIndex].toShortString();
  }

  public String getName()
  {
    return constants[nameIndex].toShortString();
  }

  public boolean isStatic()
  {
    return (accessFlags&ClassFile.ACC_STATIC)!=0;
  }

  public void markForCoding(ClassLoader classLoader)
  {
    if(!markedForCoding)
    {
      markedForCoding=true;
      for(Attribute attribute:attributes)
        if(attribute instanceof CodeAtrribute)
          ((CodeAtrribute)attribute).markForCoding(classLoader);
          
    }
    
  }

}
