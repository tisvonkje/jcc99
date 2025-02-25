package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class FconstInstruction extends Instruction
{

  private float value;

  public FconstInstruction(int position, Constant[] constants, float value)
  {
    super(position,constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("fconst %f",value);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codePushFloat(value);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
