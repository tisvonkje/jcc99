package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class LconstInstruction extends Instruction
{
  private long value;

  public LconstInstruction(ByteBuffer buffer, Constant[] constants, long value)
  {
    super(buffer,constants);
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
