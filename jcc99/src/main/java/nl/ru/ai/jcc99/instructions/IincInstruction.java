package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class IincInstruction extends Instruction
{
  private int local;
  private int value;

  public IincInstruction(ByteBuffer buffer, Constant[] constants, int local, int value)
  {
    super(buffer,constants);
    this.local=local;
    this.value=value;
  }

  public String toString()
  {
    return String.format("iinc %d,%d",local,value);
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
