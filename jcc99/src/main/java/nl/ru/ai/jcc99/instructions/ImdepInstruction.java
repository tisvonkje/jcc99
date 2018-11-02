package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class ImdepInstruction extends Instruction
{

  private int value;

  public ImdepInstruction(Constant[] constants, int value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("imdep %d",value);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
