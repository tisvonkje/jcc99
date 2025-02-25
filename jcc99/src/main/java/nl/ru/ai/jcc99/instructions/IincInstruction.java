package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class IincInstruction extends Instruction
{
  private int local;
  private int value;

  public IincInstruction(int position, Constant[] constants, int local, int value)
  {
    super(position,constants);
    this.local=local;
    this.value=value;
  }

  public String toString()
  {
    return String.format("iinc %d,%d",local,value);
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeIntInc(method.getParameterUnits(),local,value);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
