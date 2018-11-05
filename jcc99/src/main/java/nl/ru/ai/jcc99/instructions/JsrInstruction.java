package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class JsrInstruction extends Instruction
{
  private int offset;

  public JsrInstruction(ByteBuffer buffer, Constant[] constants, int offset)
  {
    super(buffer,constants);
    this.offset=offset;
  }
  
  public String toString()
  {
    return String.format("jsr %d",offset);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
