package nl.ru.ai.jcc99;

import java.io.IOException;
import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.attributes.Attribute;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.constants.DoubleConstant;
import nl.ru.ai.jcc99.constants.LongConstant;

public class ClassFile
{
  private static final short ACC_PUBLIC=0x0001;
  private static final short ACC_FINAL=0x0010;
  private static final short ACC_SUPER=0x0020;
  private static final short ACC_INTERFACE=0x0200;
  private static final short ACC_ABSTRACT=0x0400;
  private static final short ACC_SYNTHETIC=0x1000;
  private static final short ACC_ANNOTATION=0x2000;
  private static final short ACC_ENUM=0x4000;
  /*
   * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
   * javap -c App.class 
   */
  private int magic;
  private short minor;
  private short major;
  /*
   * constants, note that some slots may be unused (null)
   */
  private Constant[] constants;
  private short accessFlags;
  private short thisClass;
  private short superClass;
  private Attribute[] attributes;
  /*
   * Constructor
   */
  public ClassFile(ByteBuffer buffer) throws IOException, ClassLoaderException
  {
    magic=buffer.getInt();
    minor=buffer.getShort();
    major=buffer.getShort();
    if(major!=49 || minor!=0)
      throw new ClassLoaderException(String.format("Invalid class file version, expected 49,0, got %d,%d",major,minor));
    short constantPoolCount=buffer.getShort();
    constants=new Constant[constantPoolCount];
    for(int i=1;i<constantPoolCount;i++)
    {
      constants[i]=Constant.create(constants,buffer);
      /*
       * We have to skip a constant slot for Long and Double constants
       * From JVM spec: In retrospect, making 8-byte constants take two constant pool entries was a poor choice.
       * I agree, but who ever thought it was a good idea? UTF8 slots have fixed length so why?
       */
      if(constants[i] instanceof LongConstant || constants[i] instanceof DoubleConstant)
        i++;
    }
    /*
     * Get access flags
     */
    accessFlags=buffer.getShort();
    /*
     * Get (this) class
     */
    thisClass=buffer.getShort();
    /*
     * Get super class
     */
    superClass=buffer.getShort();
    /*
     * Get interfaces
     */
    short interfaceCount=buffer.getShort();
    short [] interfaces=new short[interfaceCount];
    for(int i=0;i<interfaceCount;i++)
      interfaces[i]=buffer.getShort();
    /*
     * Get fields
     */
    short fieldCount=buffer.getShort();
    Field [] fields=new Field[fieldCount];
    for(int i=0;i<fieldCount;i++)
      fields[i]=new Field(constants,buffer);
    /*
     * Get methods
     */
    short methodCount=buffer.getShort();
    Method [] methods=new Method[methodCount];
    for(int i=0;i<methodCount;i++)
      methods[i]=new Method(constants,buffer);
    /*
     * Get attributes
     */
    short attributeCount=buffer.getShort();
    attributes=new Attribute[attributeCount];
    for(int i=0;i<attributeCount;i++)
      attributes[i]=Attribute.create(constants,buffer);
    /*
     * Check for end of file
     */
    if(buffer.hasRemaining())
      throw new RuntimeException("Trailing bytes in classfile");
  }
  /**
   * Return name of class (in path notation, for example com/mysql/fabric/ServerRole)
   * @return name of class
   */
  public String getName()
  {
    return constants[thisClass].toShortString();
  }
}
