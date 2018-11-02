package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class Ldc2WInstruction extends Instruction
{
  private int index;

  public Ldc2WInstruction(Constant[] constants, int index)
  {
    super(constants);
    this.index=index;
  }
  
  public String toString()
  {
    return String.format("ldc2_w %d (%s)",index,constants[index].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
