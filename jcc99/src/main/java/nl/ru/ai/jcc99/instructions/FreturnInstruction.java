package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class FreturnInstruction extends Instruction
{

  public FreturnInstruction(ByteBuffer buffer, Constant[] constants)
  {
    super(buffer,constants);
  }
  
  public String toString()
  {
    return "freturn";
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
