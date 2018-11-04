package nl.ru.ai.jcc99;

import java.io.IOException;
import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.attributes.Attribute;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.constants.DoubleConstant;
import nl.ru.ai.jcc99.constants.LongConstant;

public class ClassFile
{
  public static final short ACC_PUBLIC=0x0001;
  public static final short ACC_PRIVATE=0x0002;
  public static final short ACC_PROTECTED=0x0004;
  public static final short ACC_STATIC=0x0008;
  public static final short ACC_VOLATILE=0x0040;
  public static final short ACC_TRANSIENT=0x0080;
  public static final short ACC_FINAL=0x0010;
  public static final short ACC_SUPER=0x0020;
  public static final short ACC_NATIVE=0x0100;
  public static final short ACC_INTERFACE=0x0200;
  public static final short ACC_ABSTRACT=0x0400;
  public static final short ACC_SYNTHETIC=0x1000;
  public static final short ACC_ANNOTATION=0x2000;
  public static final short ACC_ENUM=0x4000;
  /*
   * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
   * javap -c App.class 
   */
  @SuppressWarnings("unused")
  private int magic;
  private short minor;
  private short major;
  /*
   * constants, note that some slots may be unused (null)
   */
  private Constant[] constants;
  @SuppressWarnings("unused")
  private short accessFlags;
  private short thisClass;
  @SuppressWarnings("unused")
  private short superClass;
  private Attribute[] attributes;
  private short[] interfaces;
  private Field[] fields;
  private Method[] methods;
  private boolean analyzed;

  private Integer size;
  /*
   * Constructor
   */
  public ClassFile(ByteBuffer buffer) throws IOException, ClassLoaderException
  {
    this.analyzed=false;
    this.size=null;
    magic=buffer.getInt();
    minor=buffer.getShort();
    major=buffer.getShort();
    if(major!=49||minor!=0)
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
      if(constants[i] instanceof LongConstant||constants[i] instanceof DoubleConstant)
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
    interfaces=new short[interfaceCount];
    for(int i=0;i<interfaceCount;i++)
      interfaces[i]=buffer.getShort();
    /*
     * Get fields
     */
    short fieldCount=buffer.getShort();
    fields=new Field[fieldCount];
    for(int i=0;i<fieldCount;i++)
      fields[i]=new Field(constants,buffer);
    /*
     * Get methods
     */
    short methodCount=buffer.getShort();
    methods=new Method[methodCount];
    for(int i=0;i<methodCount;i++)
      methods[i]=new Method(constants,this,buffer);
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

  public String toString()
  {
    StringBuffer buffer=new StringBuffer();
    for(Method method : methods)
    {
      buffer.append(method.toString());
      buffer.append('\n');
    }
    return new String(buffer);
  }
  public Method[] getMethods()
  {
    return methods;
  }
  public int getSizeAndCalculateFieldOffsets(int startOffset, ClassLoader classLoader)
  {
    if(size==null)
    {
      Constant superClassConstant=constants[superClass];
      if(superClassConstant!=null)
      {
        ClassFile parentClass=classLoader.getClassFile(superClassConstant.toShortString());
        if(parentClass==null)
          throw new RuntimeException("Invalid parent class");
        startOffset=parentClass.getSizeAndCalculateFieldOffsets(startOffset,classLoader);
      }
      for(Field field : fields)
        if(field.isNonStatic())
          startOffset+=field.getSizeAndSetOffset(startOffset);
      size=startOffset;
    }
    return size;
  }
  public void analyze(ClassLoader classLoader)
  {
    /*
     * Already marked? nothing to do
     */
    if(analyzed)
      return;
    analyzed=true;
    /*
     * Calculate size and field offsets
     */
    getSizeAndCalculateFieldOffsets(0,classLoader);
    /*
     * Mark class initialization if it exists
     */
    System.out.printf("looking for <%s>\n",getName()+".<clinit>:()V");
    Method clinit=classLoader.getStaticMethod(getName()+".<clinit>:()V");
    if(clinit!=null)
      clinit.analyze(classLoader);
  }
  
  public Integer getSize()
  {
    return size;
  }
  
  public Field getField(String fieldName)
  {
    throw new RuntimeException("notyet");
  }
}
