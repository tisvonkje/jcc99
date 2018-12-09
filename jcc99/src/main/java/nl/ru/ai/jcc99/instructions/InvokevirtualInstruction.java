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

  public InvokevirtualInstruction(int position, Constant[] constants, int methodIndex)
  {
    super(position,constants);
    this.methodIndex=methodIndex;
  }
  
  public void analyze(ClassLoader classLoader, Method method)
  {
    String name = constants[methodIndex].toShortString();
	subMethod=classLoader.getDynamicMethod(name);
    if(subMethod==null)
      Util.error("Cannot analyze '%s' for invokevirtual",name);
    // Analyzing the method itself is done later (all non static methods of needed classes should be coded)
  }
  
  public String toString()
  {
    return String.format("invokevirtual %d (%s)",methodIndex,constants[methodIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeDynamicCall(subMethod);
  }
}
