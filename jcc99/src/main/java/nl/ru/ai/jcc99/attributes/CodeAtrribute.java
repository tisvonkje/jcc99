package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;
import java.util.List;

import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.instructions.Instruction;

public class CodeAtrribute extends Attribute
{
  private short maxStack;
  private short maxLocals;
  private int codeLength;
  private ExceptionEntry[] exceptions;
  private Attribute[] attributes;
  private List<Instruction> instructions;

  public CodeAtrribute(Constant[] constants, ByteBuffer buffer)
  {
    super(constants);
    maxStack=buffer.getShort();
    maxLocals=buffer.getShort();
    codeLength=buffer.getInt();
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
    return String.format("Code(maxStack=%d, maxLocals=%d, code=%s)",maxStack,maxLocals,instructions);
  }

}
