package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.Util;
import nl.ru.ai.jcc99.constants.Constant;

public class InvokedynamicInstruction extends Instruction
{

  private int methodIndex;
  private byte zero1;
  private byte zero2;

  public InvokedynamicInstruction(ByteBuffer buffer, Constant[] constants, int methodIndex, byte zero1, byte zero2)
  {
    super(buffer,constants);
    this.methodIndex=methodIndex;
    this.zero1=zero1;
    this.zero2=zero2;
  }
  
  public void analyze(ClassLoader classLoader, Method method)
  {
    String methodName=constants[methodIndex].toShortString();
    Method subMethod=classLoader.getDynamicMethod(methodName);
    if(subMethod==null)
      Util.error("Cannot mark '%s' for coding",methodName);
    else
      subMethod.analyze(classLoader);
  }
  
  public String toString()
  {
    return String.format("invokedynamic %d (%s),%d,%d",methodIndex,constants[methodIndex].toShortString(),zero1,zero2);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
