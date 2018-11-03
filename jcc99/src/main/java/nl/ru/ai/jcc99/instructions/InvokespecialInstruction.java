package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.Util;
import nl.ru.ai.jcc99.constants.Constant;

public class InvokespecialInstruction extends Instruction
{
  private int methodIndex;
  private Method subMethod;

  public InvokespecialInstruction(Constant[] constants, int methodIndex)
  {
    super(constants);
    this.methodIndex=methodIndex;
  }
  
  public void analyze(ClassLoader classLoader)
  {
    String methodName=constants[methodIndex].toShortString();
    subMethod=classLoader.getDynamicMethod(methodName);
    if(subMethod==null)
      Util.error("Cannot mark '%s' for coding",methodName);
    else
      subMethod.analyze(classLoader);
  }
  
  public String toString()
  {
    return String.format("invokespecial %d (%s)",methodIndex,constants[methodIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeCall(subMethod);
  }
}
