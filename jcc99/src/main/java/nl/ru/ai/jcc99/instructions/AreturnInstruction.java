package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class AreturnInstruction extends Instruction
{

  public AreturnInstruction(int position, Constant[] constants)
  {
    super(position,constants);
  }

  public String toString()
  {
    return "areturn";
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
