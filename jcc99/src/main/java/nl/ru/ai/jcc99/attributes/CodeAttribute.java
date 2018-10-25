package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;
import java.util.List;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.instructions.Instruction;

public class CodeAttribute extends Attribute
{
  private short maxStack;
  private short maxLocals;
  private ExceptionEntry[] exceptions;
  private Attribute[] attributes;
  private List<Instruction> instructions;

  public CodeAttribute(Constant[] constants, ByteBuffer buffer)
  {
    super(constants);
    maxStack=buffer.getShort();
    maxLocals=buffer.getShort();
    int codeLength=buffer.getInt();
    byte [] code=new byte[codeLength];
    buffer.get(code);
    instructions=Instruction.create(constants,ByteBuffer.wrap(code));
    short exceptionTableLength=buffer.getShort();
    exceptions=new ExceptionEntry[exceptionTableLength];
    for(int i=0;i<exceptionTableLength;i++)
      exceptions[i]=new ExceptionEntry(buffer);
    short attributesCount=buffer.getShort();
    attributes=new Attribute[attributesCount];
    for(int i=0;i<attributesCount;i++)
      attributes[i]=Attribute.create(constants,buffer);
    if(buffer.hasRemaining())
      throw new RuntimeException("traling data");
  }
  
  public String toString()
  {
    StringBuffer buffer=new StringBuffer();
    buffer.append('[');
    for(int i=0;i<attributes.length;i++)
    {
      buffer.append(attributes[i]);
      if(i!=attributes.length-1)
        buffer.append(',');
    }
    buffer.append(']');
    return String.format("Code(maxStack=%d, maxLocals=%d, code=%s, attributes=%s)",maxStack,maxLocals,instructions,new String(buffer));
  }

  public void markForCoding(ClassLoader classLoader)
  {
    for(Instruction instruction:instructions)
      instruction.markForCoding(classLoader);
  }

}
