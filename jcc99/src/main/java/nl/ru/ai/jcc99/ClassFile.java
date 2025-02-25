package nl.ru.ai.jcc99;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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
  private ClassLoader classLoader;
  private List<Method> dynamicMethodOffsets;
  /*
   * Constructor
   */
  public ClassFile(ClassLoader classLoader, ByteBuffer buffer) throws IOException, ClassLoaderException
  {
    this.classLoader=classLoader;
    this.analyzed=false;
    this.size=null;
    this.dynamicMethodOffsets=null;
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
      fields[i]=new Field(this,constants,buffer);
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
  public Field[] getFields()
  {
    return fields;
  }
  public int getSizeAndCalculateFieldOffsets(int startOffset)
  {
    if(size==null)
    {
      Constant superClassConstant=constants[superClass];
      if(superClassConstant!=null)
      {
        ClassFile parentClass=classLoader.getClassFile(superClassConstant.toShortString());
        if(parentClass==null)
          throw new RuntimeException("Invalid parent class");
        startOffset=parentClass.getSizeAndCalculateFieldOffsets(startOffset);
      }
      for(Field field : fields)
        if(field.isNonStatic())
          startOffset+=field.getSizeAndSetOffset(startOffset);
      size=startOffset;
    }
    return size;
  }
  public void analyze()
  {
    /*
     * Already marked? nothing to do
     */
    if(analyzed)
      return;
    analyzed=true;
    classLoader.collect(this);
    /*
     * Calculate size and field offsets
     */
    getSizeAndCalculateFieldOffsets(0);
    /*
     * Determine dynamic method offsets
     */
    getDynamicMethodOffsets();
    /*
     * Mark class initialization if it exists
     */
    Method clinit=classLoader.getStaticMethod(getName()+".<clinit>:()V");
    if(clinit!=null)
    {
      classLoader.addInitialization(clinit);
      clinit.analyze(classLoader);
    }
    /*
     * Analyze all field classes
     */
    for(Field field:fields)
      field.analyze(classLoader);
    /*
     * Analyze superclass if present
     */
    Constant superClassConstant=constants[superClass];
    if(superClassConstant!=null)
      classLoader.getClassFile(superClassConstant.toShortString()).analyze();
    /*
     * Analyze interfaces
     */
    for(short interfaceIndex:interfaces)
      classLoader.getClassFile(constants[interfaceIndex].toShortString()).analyze();
  }

  private List<Method> getDynamicMethodOffsets()
  {
    if(dynamicMethodOffsets==null)
    {
      Constant superClassConstant=constants[superClass];
      if(superClassConstant!=null)
      {
        ClassFile parentClass=classLoader.getClassFile(superClassConstant.toShortString());
        if(parentClass==null)
          throw new RuntimeException("Invalid parent class");
        dynamicMethodOffsets=new ArrayList<Method>(parentClass.getDynamicMethodOffsets());
      } else
        dynamicMethodOffsets=new ArrayList<Method>();
      /*
       * Override our methods
       */
      for(Method method:methods)
      {
        //FIXME: checking for constructor is a bit ugly this way
        if(!method.isStatic() && !"<init>".equals(method.getName()))
        {
          /*
           * Check if it is defined by one of our ancestors
           */
          int i;
          for(i=0;i<dynamicMethodOffsets.size();i++)
            if(dynamicMethodOffsets.get(i).getNameAndDescriptor().equals(method.getNameAndDescriptor()))
              break;
          if(i!=dynamicMethodOffsets.size())
          {
            method.setOffset(i);
            dynamicMethodOffsets.set(i,method);
          } else
          {
            method.setOffset(dynamicMethodOffsets.size());
            dynamicMethodOffsets.add(method);
          }
        }
      }
    }
    return dynamicMethodOffsets;
  }
  public Integer getSize()
  {
    return size;
  }

  public Field getField(String fieldName)
  {
    for(Field field : fields)
      if(fieldName.equals(field.getName()))
        return field;
    /*
     * Did not find field, check parent class
     */
    String superClass=getSuperClass();
    if(superClass==null)
      return null;
    ClassFile classFile=classLoader.getClassFile(superClass);
    return classFile.getField(fieldName);
  }
  public void codeVector(Coder coder)
  {
    if(analyzed)
    {
      coder.codeLabel("_Vector_",this);
      coder.codeWord("_Vector_java_lang_Class");
      coder.codeWord("_Info_",this);
      for(Method method : dynamicMethodOffsets)
        if(isInterface())
          coder.codeWord(0);
        else
          coder.codeWord(method);
    }
  }
  public void codeInfo(Coder coder)
  {
    coder.codeLabel("_Info_",this);
    coder.codeWord("_Vector_java_lang_ClassInfo");
    coder.codeWord("_Name_",this);
    Constant parentClass=constants[superClass];
    if(parentClass!=null)
    {
      ClassFile parent=classLoader.getClassFile(parentClass.toShortString());
      coder.codeWord("_Vector_",parent);
    } else
      coder.codeWord(0);
    coder.codeWord("_Interfaces_",this);
  }
  public void codeInterfaces(Coder coder)
  {
    coder.codeLabel("_Interfaces_",this);
    coder.codeWord(0); //FIXME: should be classvector of Array of Class
    coder.codeWord(interfaces.length);
    for(short index:interfaces)
      coder.codeWord("_Vector_",classLoader.getClassFile(constants[index].toShortString()));
  }
  public void codeName(Coder coder)
  {
    coder.codeLabel("_Name_",this);
    coder.codeWord("_Vector_java_lang_String");
    coder.codeWord(".+4");
    coder.codeWord(0); //FIXME: classvector for char []
    String string=getName().replace('/','.');
    coder.codeWord(string.length());
    for(int i=0;i<string.length();i++)
      coder.codeChar(string.charAt(i));
  }
  public void analyzeDynamicMethods()
  {
    for(Method method:methods)
      if(!method.isStatic())
        method.analyze(classLoader);
  }      
  public String getSuperClass()
  {
    return constants[superClass].toShortString();
  }
  
  public boolean isInterface()
  {
    return (accessFlags&ACC_INTERFACE)!=0;
  }
  public void codeDebug(Coder coder, ClassLoader loader)
  {
	coder.codeLabel("Debug_Class_",this);
	int numberOfNonStaticFields=0;
	for(Field field:fields)
	  if(field.isNonStatic())
		numberOfNonStaticFields++;
	coder.codeWord(numberOfNonStaticFields);
	for(Field field:fields)
	  field.codeDebug(coder,loader);
  }
}
