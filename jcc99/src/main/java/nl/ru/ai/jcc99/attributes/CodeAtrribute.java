package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public class CodeAtrribute extends Attribute
{
  private short maxStack;
  private short maxLocals;
  private int codeLength;
  private byte[] code;
  private ExceptionEntry[] exceptions;
  private Attribute[] attributes;

  public CodeAtrribute(Constant[] constants, ByteBuffer buffer)
  {
    super(constants);
    maxStack=buffer.getShort();
    maxLocals=buffer.getShort();
    codeLength=buffer.getInt();
    code=new byte[codeLength];
    buffer.get(code);
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
    for(int i=0;i<code.length;i++)
      buffer.append(String.format("%02x",code[i]));
    return String.format("Code(maxStack=%d, maxLocals=%d, code=%s)",maxStack,maxLocals,new String(buffer));
  }

}
