package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class LconstInstruction extends Instruction
{
  private long value;

  public LconstInstruction(Constant[] constants, long value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("lconst %d",value);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
