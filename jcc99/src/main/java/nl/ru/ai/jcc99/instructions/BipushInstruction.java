package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class BipushInstruction extends Instruction
{
  private int value;

  public BipushInstruction(ByteBuffer buffer, Constant[] constants, int value)
  {
    super(buffer,constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("bipush %d",value);
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codePushByte(value);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
