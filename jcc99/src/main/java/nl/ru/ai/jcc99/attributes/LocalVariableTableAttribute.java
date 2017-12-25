package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.constants.Constant;

public class LocalVariableTableAttribute extends Attribute
{
  private LocalVariable[] localVariables;

  public LocalVariableTableAttribute(Constant[] constants, ByteBuffer buffer)
  {
    super(constants);
    int localVariableTableLength=buffer.getShort();
    localVariables=new LocalVariable [localVariableTableLength];
    for(int i=0;i<localVariableTableLength;i++)
      localVariables[i]=new LocalVariable(constants,buffer);
  }
  
  public String toString()
  {
    StringBuffer buffer=new StringBuffer();
    buffer.append("LocalVariableTable(");
    for(int i=0;i<localVariables.length;i++)
    {
      buffer.append(localVariables[i].toString());
      if(i!=localVariables.length-1)
        buffer.append(',');
    }
    buffer.append(')');
    return new String(buffer);
  }

}
