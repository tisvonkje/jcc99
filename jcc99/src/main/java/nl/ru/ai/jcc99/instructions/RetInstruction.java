package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class RetInstruction extends Instruction
{
  @SuppressWarnings("unused")
  private int local;

  public RetInstruction(ByteBuffer buffer, Constant[] constants, int local)
  {
    super(buffer,constants);
    this.local=local;
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
