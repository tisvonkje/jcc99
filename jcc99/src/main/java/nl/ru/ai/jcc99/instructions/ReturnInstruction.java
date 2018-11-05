package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class ReturnInstruction extends Instruction
{

  public ReturnInstruction(ByteBuffer buffer, Constant[] constants)
  {
    super(buffer,constants);
  }
  
  public String toString()
  {
    return "return";
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeReturn();
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }

}
