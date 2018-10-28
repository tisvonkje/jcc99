package nl.ru.ai.jcc99;

import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import nl.ru.ai.jcc99.attributes.Attribute;
import nl.ru.ai.jcc99.attributes.CodeAttribute;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.types.ArrayType;
import nl.ru.ai.jcc99.types.BooleanType;
import nl.ru.ai.jcc99.types.CharacterType;
import nl.ru.ai.jcc99.types.ClassType;
import nl.ru.ai.jcc99.types.DoubleType;
import nl.ru.ai.jcc99.types.FloatType;
import nl.ru.ai.jcc99.types.IntegerType;
import nl.ru.ai.jcc99.types.LongType;
import nl.ru.ai.jcc99.types.MethodType;
import nl.ru.ai.jcc99.types.ShortType;
import nl.ru.ai.jcc99.types.Type;
import nl.ru.ai.jcc99.types.VoidType;

public class Method
{
  private Constant[] constants;
  private ClassFile classFile;
  private short accessFlags;
  private short nameIndex;
  private short descriptorIndex;
  private Attribute[] attributes;
  private CodeAttribute code;
  private boolean markedForCoding;
  private int parameterUnits;

  public Method(Constant[] constants, ClassFile classFile, ByteBuffer buffer)
  {
    this.constants=constants;
    this.classFile=classFile;
    this.markedForCoding=false;
    this.code=null;
    accessFlags=buffer.getShort();
    nameIndex=buffer.getShort();
    descriptorIndex=buffer.getShort();
    short attributeCount=buffer.getShort();
    attributes=new Attribute[attributeCount];
    for(int i=0;i<attributeCount;i++)
    {
      attributes[i]=Attribute.create(constants,buffer);
      if(attributes[i] instanceof CodeAttribute)
        code=(CodeAttribute)attributes[i];
    }
    /*
     * Convert descriptor into type structure
     */
    Type type=Util.convert(constants[descriptorIndex].toShortString());
    System.out.println(type);
    /*
     * Determine how many units the parameters take in a stack frame
     */
    parameterUnits=type.parameterUnitSize();

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

  public String getFullName()
  {
    return classFile.getName()+"."+getName()+":"+getDescriptor();
  }

  public boolean isStatic()
  {
    return (accessFlags&ClassFile.ACC_STATIC)!=0;
  }

  public boolean isNative()
  {
    return (accessFlags&ClassFile.ACC_NATIVE)!=0;
  }

  public void markForCoding(ClassLoader classLoader)
  {
    /*
     * Already marked? nothing to do
     */
    if(markedForCoding)
      return;
    markedForCoding=true;
    /*
     * Be sure our own class is marked for coding which will marked class initialization methods
     */
    classFile.markForCoding(classLoader);
    /*
     * Mark methods used in our code
     */
    if(code!=null)
      code.markForCoding(classLoader);
  }

  public boolean isMarkedForCoding()
  {
    return markedForCoding;
  }

  public void code(Coder coder)
  {
    coder.codeComment("Method "+getFullName());
    coder.codeLabel(this);
    code.code(parameterUnits,coder);
  }

}
