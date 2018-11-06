package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class Lookupswitch extends Instruction
{
  private int defaultValue;
  private Pair[] pairs;

  public Lookupswitch(int position,Constant[] constants, ByteBuffer buffer)
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
    int npairs=buffer.getInt();
    pairs=new Pair[npairs];
    for(int i=0;i<pairs.length;i++)
      pairs[i]=new Pair(buffer.getInt(),buffer.getInt());
  }
  
  public String toString()
  {
    return String.format("lookupswitch %d,%s",defaultValue,pairs);
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
