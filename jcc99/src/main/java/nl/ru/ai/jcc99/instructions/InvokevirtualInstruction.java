package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class InvokevirtualInstruction extends Instruction
{
  private int methodIndex;

  public InvokevirtualInstruction(Constant[] constants, int methodIndex)
  {
    super(constants);
    this.methodIndex=methodIndex;
  }
  
  public String toString()
  {
    return String.format("invokevirtual %d (%s)",methodIndex,constants[methodIndex].toShortString());
  }

  public void markForCoding(ClassLoader classLoader)
  {
    Method subMethod=classLoader.getDynamicMethod(constants[methodIndex].toShortString());
    subMethod.markForCoding(classLoader);
  }

}
