package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class LdcWInstruction extends Instruction
{
  private int index;

  public LdcWInstruction(int position, Constant[] constants, int index)
  {
    super(position,constants);
    this.index=index;
  }
  
  public void analyze(ClassLoader loader, Method method)
  {
    
  }

  public String toString()
  {
    return String.format("ldc_w %d (%s)",index,constants[index].toShortString());
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
