package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class AconstNullInstruction extends Instruction
{

  public AconstNullInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "aconst_null";
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
