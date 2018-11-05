package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.Util;
import nl.ru.ai.jcc99.constants.Constant;

public class InvokevirtualInstruction extends Instruction
{
  private int methodIndex;
  private Method subMethod;

  public InvokevirtualInstruction(ByteBuffer buffer, Constant[] constants, int methodIndex)
  {
    super(buffer,constants);
    this.methodIndex=methodIndex;
  }
  
  public void analyze(ClassLoader classLoader, Method method)
  {
    String name = constants[methodIndex].toShortString();
	subMethod=classLoader.getDynamicMethod(name);
    if(subMethod==null)
      Util.error("Cannot mark '%s' for coding",name);
    else
      subMethod.analyze(classLoader);
  }
  
  public String toString()
  {
    return String.format("invokevirtual %d (%s)",methodIndex,constants[methodIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeCall(subMethod);
  }
}
