package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class ReturnInstruction extends Instruction
{

  public ReturnInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "return";
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeReturn();
  }

}
