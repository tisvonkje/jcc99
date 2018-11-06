package nl.ru.ai.jcc99;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.attributes.Attribute;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.types.Type;

public class Field
{
  private short accessFlags;
  private short nameIndex;
  private short descriptorIndex;
  private Attribute[] attributes;
  private ClassFile classFile;
  private Constant[] constants;
  private Integer offset;

  public Field(ClassFile classFile,Constant[] constants, ByteBuffer buffer)
  {
    this.classFile=classFile;
    this.constants=constants;
    this.offset=null;
    accessFlags=buffer.getShort();
    nameIndex=buffer.getShort();
    descriptorIndex=buffer.getShort();
    short attributeCount=buffer.getShort();
    attributes=new Attribute[attributeCount];
    for(int i=0;i<attributeCount;i++)
      attributes[i]=Attribute.create(constants,buffer);
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

  public boolean isNonStatic()
  {
    return (accessFlags&ClassFile.ACC_STATIC)==0;
  }
  
  public boolean isStatic()
  {
    return (accessFlags&ClassFile.ACC_STATIC)!=0;
  }

  public int getSizeAndSetOffset(int offset)
  {
    this.offset=offset;
    Type type=Util.convert(constants[descriptorIndex].toShortString());
    return type.unitSize();
  }

  public String getName()
  {
    return constants[nameIndex].toShortString();
  }

  public Integer getOffset()
  {
    return offset;
  }

  public String getFullName()
  {
    return classFile.getName()+"."+getName();
  }

  public void code(ClassLoader classLoader, Coder coder)
  {
    throw new RuntimeException("notyet");
  }
}
