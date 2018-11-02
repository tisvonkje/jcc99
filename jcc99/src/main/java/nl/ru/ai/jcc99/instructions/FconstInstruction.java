package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class FconstInstruction extends Instruction
{

  private float value;

  public FconstInstruction(Constant[] constants, float value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("fconst %f",value);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
