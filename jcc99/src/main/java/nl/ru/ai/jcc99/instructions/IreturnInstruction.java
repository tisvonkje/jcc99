package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

//FIXME maybe merge all D.I,L,A-returnInstruction classes
public class IreturnInstruction extends Instruction
{

  public IreturnInstruction(int position, Constant[] constants)
  {
    super(position,constants);
  }
  
  public String toString()
  {
    return "ireturn";
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeReturnSingle(method.getParameterUnits());
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
