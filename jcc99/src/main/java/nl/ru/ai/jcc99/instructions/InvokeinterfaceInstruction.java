package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class InvokeinterfaceInstruction extends Instruction
{
  private int methodIndex;
  private int count;
  private byte zero;

  public InvokeinterfaceInstruction(ByteBuffer buffer, Constant[] constants, int methodIndex, int count, byte zero)
  {
    super(buffer,constants);
    this.methodIndex=methodIndex;
    this.count=count;
    this.zero=zero;
  }
  
  public String toString()
  {
    return String.format("invokedynamic %d (%s),%d,%d",methodIndex,constants[methodIndex].toShortString(),count,zero);
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
