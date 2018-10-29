package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.Util;
import nl.ru.ai.jcc99.constants.Constant;

public class InvokestaticInstruction extends Instruction
{
  private int methodIndex;
  private Method method;

  public InvokestaticInstruction(Constant[] constants, int methodIndex)
  {
    super(constants);
    this.methodIndex=methodIndex;
  }
  
  public void analyze(ClassLoader classLoader)
  {
    String methodName=constants[methodIndex].toShortString();
    method=classLoader.getStaticMethod(methodName);
    if(method==null)
      Util.error("Cannot mark '%s' for coding",methodName);
    else
      method.analyze(classLoader);
  }
  
  public String toString()
  {
    return String.format("invokestatic %d (%s)",methodIndex,constants[methodIndex].toShortString());
  }
  
  public void code(int parameterUnits, Coder coder)
  {
    coder.codeCall(method);
  }

}
