package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class LdcInstruction extends Instruction
{
  private int index;

  public LdcInstruction(ByteBuffer buffer, Constant[] constants, int index)
  {
    super(buffer,constants);
    this.index=index;
  }
  
  public String toString()
  {
    return String.format("ldc %d (%s)",index,constants[index].toShortString());
  }
  
  public void analyze(ClassLoader loader, Method method)
  {
    constants[index].analyze(loader);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    constants[index].codeLoad(coder);
  }

}
