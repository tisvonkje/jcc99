package nl.ru.ai.jcc99;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
   * ClassFile 
   * {
   *   u4             magic;
   *   u2             minor_version;
   *   u2             major_version;
   *   u2             constant_pool_count;
   *   cp_info        constant_pool[constant_pool_count-1];
   *   u2             access_flags;
   *   u2             this_class;
   *   u2             super_class;
   *   u2             interfaces_count;
   *   u2             interfaces[interfaces_count];
   *   u2             fields_count;
   *   field_info     fields[fields_count];
   *   u2             methods_count;
   *   method_info    methods[methods_count];
   *   u2             attributes_count;
   *   attribute_info attributes[attributes_count];
   * }
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
  public ClassFile(String folderName, String classFileName) throws IOException
  {
    File folder=new File(folderName);
    File file=new File(folderName,classFileName.replace(".","/")+".class");
    FileInputStream inputStream=new FileInputStream(file);
    FileChannel channel=inputStream.getChannel();
    ByteBuffer buffer=ByteBuffer.allocate((int)file.length());
    channel.read(buffer);
    buffer.flip();
    magic=buffer.getInt();
    System.out.printf("%04x\n",magic);
    minor=buffer.getShort();
    major=buffer.getShort();
    System.out.printf("major=%d, minor=%d\n",major,minor);
    if(major!=49 || minor!=0)
      throw new RuntimeException("Invalid class file version");
    short constantPoolCount=buffer.getShort();
    System.out.printf("constant pool count=%d\n",constantPoolCount);
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
    for(int i=0;i<constants.length;i++)
      if(constants[i]!=null)
        System.out.printf("%d: %s\n",i,constants[i]);
    /*
     * Get access flags
     */
    accessFlags=buffer.getShort();
    System.out.printf("access flags:");
    if((accessFlags&ACC_PUBLIC)!=0)
      System.out.printf(" ACC_PUBLIC");
    if((accessFlags&ACC_FINAL)!=0)
      System.out.printf(" ACC_FINAL");
    if((accessFlags&ACC_SUPER)!=0)
      System.out.printf(" ACC_SUPER");
    if((accessFlags&ACC_INTERFACE)!=0)
      System.out.printf(" ACC_INTERFACE");
    if((accessFlags&ACC_ABSTRACT)!=0)
      System.out.printf(" ACC_ABSTRACT");
    if((accessFlags&ACC_SYNTHETIC)!=0)
      System.out.printf(" ACC_SYNTHETIC");
    if((accessFlags&ACC_ANNOTATION)!=0)
      System.out.printf(" ACC_ANNOTATION");
    if((accessFlags&ACC_ENUM)!=0)
      System.out.printf(" ACC_ENUM");
    System.out.println();
    /*
     * Get (this) class
     */
    thisClass=buffer.getShort();
    System.out.printf("this class: %s\n",constants[thisClass].toShortString());
    /*
     * Get super class
     */
    superClass=buffer.getShort();
    System.out.printf("superclass: %s\n",constants[superClass].toShortString());
    /*
     * Get interfaces
     */
    short interfaceCount=buffer.getShort();
    short [] interfaces=new short[interfaceCount];
    System.out.print("Interfaces: ");
    for(int i=0;i<interfaceCount;i++)
    {
      interfaces[i]=buffer.getShort();
      System.out.printf("%s ",constants[interfaces[i]].toShortString());
    }
    System.out.println();
    /*
     * Get fields
     */
    System.out.println("Fields:");
    short fieldCount=buffer.getShort();
    Field [] fields=new Field[fieldCount];
    for(int i=0;i<fieldCount;i++)
    {
      fields[i]=new Field(constants,buffer);
      System.out.printf("%d: %s\n",i,fields[i]);
    }
    /*
     * Get methods
     */
    System.out.println("Methods:");
    short methodCount=buffer.getShort();
    Method [] methods=new Method[methodCount];
    for(int i=0;i<methodCount;i++)
    {
      methods[i]=new Method(constants,buffer);
      System.out.printf("%d: %s\n",i,methods[i]);
    }
    /*
     * Get attributes
     */
    System.out.println("Attributes:");
    short attributeCount=buffer.getShort();
    attributes=new Attribute[attributeCount];
    for(int i=0;i<attributeCount;i++)
    {
      attributes[i]=Attribute.create(constants,buffer);
      System.out.printf("%d: %s\n",i,attributes[i]);
    }
    /*
     * Check for end of file
     */
    if(buffer.hasRemaining())
      throw new RuntimeException("Trailing bytes in classfile");
  }
  
  /**
   * Collect all classes from the classPath
   * @param classPath
   */
  public static void collectClasses(String classPath)
  {
    for(String classPathEntry:classPath)
    {
      if(classPathEntry.toLowerCase().endsWith(".jar"))
      {
        /*
         * A jar file, we have to look inside
         */
        
      } else
      {
        /*
         * Assume it is a folder look for the class on the filesystem
         */
        
      }
    }
  }
  
  /**
   * Try to load class from the classPath
   * @param className
   * @param classPath
   * @return classFile, null if not found
   */
  public static ClassFile loadClass(String className,String [] classPath)
  {
    
    return null;
  }
}
