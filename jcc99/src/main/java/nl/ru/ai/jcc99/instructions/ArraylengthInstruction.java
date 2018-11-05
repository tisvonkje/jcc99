package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class ArraylengthInstruction extends Instruction
{

  public ArraylengthInstruction(ByteBuffer buffer, Constant[] constants)
  {
    super(buffer,constants);
  }
  
  public String toString()
  {
    return "arraylength";
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeArrayLength();
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
