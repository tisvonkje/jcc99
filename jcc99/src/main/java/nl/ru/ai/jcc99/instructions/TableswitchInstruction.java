package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class TableswitchInstruction extends Instruction
{
  private int defaultValue;
  private int lowValue;
  private int highValue;
  private int[] offsets;

  public TableswitchInstruction(int position, Constant[] constants, ByteBuffer buffer)
  {
    super(position,constants);
    /*
     * This instruction is tricky there can be some padding here, depending on the position of the bytecode
     */
    switch(buffer.position()%4)
    {
      case 1:
        buffer.get();
      case 2:
        buffer.get();
      case 3:
        buffer.get();
      case 0:
        break;
    }
    defaultValue=buffer.getInt();
    lowValue=buffer.getInt();
    highValue=buffer.getInt();
    offsets=new int[highValue-lowValue+1];
    for(int i=0;i<offsets.length;i++)
      offsets[i]=buffer.getInt();
  }
  
  public String toString()
  {
    return String.format("tableswitch %d, %d, %d, %s",defaultValue,lowValue,highValue,offsets);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
