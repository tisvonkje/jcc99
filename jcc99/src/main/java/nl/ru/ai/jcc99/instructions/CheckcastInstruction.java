package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class CheckcastInstruction extends Instruction
{
  private int classIndex;

  public CheckcastInstruction(Constant[] constants, int classIndex)
  {
    super(constants);
    this.classIndex=classIndex;
  }
  
  public String toString()
  {
    return String.format("checkcast %d (%s)",classIndex,constants[classIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
