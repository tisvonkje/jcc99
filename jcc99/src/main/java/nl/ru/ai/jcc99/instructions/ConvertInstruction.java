package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class ConvertInstruction extends Instruction
{
  private Conversion conversion;

  public ConvertInstruction(ByteBuffer buffer, Constant[] constants, Conversion conversion)
  {
    super(buffer,constants);
    this.conversion=conversion;
  }
  
  public String toString()
  {
    return conversion.toString().toLowerCase();
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
