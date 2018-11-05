package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class Multianewarray extends Instruction
{

  private int classIndex;
  private int dimensions;

  public Multianewarray(ByteBuffer buffer, Constant[] constants, int classIndex, int dimensions)
  {
    super(buffer,constants);
    this.classIndex=classIndex;
    this.dimensions=dimensions;
  }
  
  public String toString()
  {
    return String.format("multianewarray %d (%s),%d",classIndex,constants[classIndex].toShortString(),dimensions);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
