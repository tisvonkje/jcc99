package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class InstanceofInstruction extends Instruction
{
  private int classIndex;

  public InstanceofInstruction(ByteBuffer buffer, Constant[] constants, int classIndex)
  {
    super(buffer,constants);
    this.classIndex=classIndex;
  }
  
  public String toString()
  {
    return String.format("instanceof %d (%s)",classIndex,constants[classIndex].toShortString());
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
