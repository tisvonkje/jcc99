package nl.ru.ai.jcc99;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import nl.ru.ai.jcc99.attributes.Attribute;
import nl.ru.ai.jcc99.attributes.CodeAttribute;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.instructions.Instruction;
import nl.ru.ai.jcc99.types.Type;

public class Method
{
  private Constant[] constants;
  private ClassFile classFile;
  private short accessFlags;
  private short nameIndex;
  private short descriptorIndex;
  private Attribute[] attributes;
  private CodeAttribute code;
  private boolean analysed;
  private int parameterUnits;
  
  private Map<Integer,String> labels;

  public Method(Constant[] constants, ClassFile classFile, ByteBuffer buffer)
  {
    this.constants=constants;
    this.classFile=classFile;
    this.analysed=false;
    this.code=null;
    labels=new HashMap<Integer,String>();
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
    /*
     * Determine how many units the parameters take in a stack frame
     */
    parameterUnits=type.parameterUnitSize();
    /*
     * Nonstatic methods have (hidden) first parameter this
     */
    if(!isStatic())
      parameterUnits++;
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

  public void analyze(ClassLoader classLoader)
  {
    /*
     * Already marked? nothing to do
     */
    if(analysed)
      return;
    analysed=true;
    /*
     * Be sure our own class is analyzed which will marked class initialization methods
     */
    classFile.analyze(classLoader);
    /*
     * Analyze the code
     */
    if(code!=null)
      code.analyze(classLoader,this);
  }

  public boolean isAnalyzed()
  {
    return analysed;
  }

  public void code(ClassLoader classLoader, Coder coder)
  {
    coder.codeComment("Method "+getFullName());
    coder.codeLabel(this);
    code.code(classLoader,this,coder);
  }

  public int getParameterUnits()
  {
    return parameterUnits;
  }

  public String getLabel(ClassLoader classLoader, int location)
  {
    if(labels.containsKey(location))
      return labels.get(location);
    String label=classLoader.getNextLabel();
    labels.put(location,label);
    return label;
  }

  public void codeLabel(Instruction instruction, Coder coder)
  {
    int location=instruction.getLocation();
    if(labels.containsKey(location))
      coder.codeLabel(labels.get(location));
  }

}
