package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class DupInstruction extends Instruction
{

  public DupInstruction(int position, Constant[] constants)
  {
    super(position,constants);
  }
  
  public String toString()
  {
    return "dup";
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeDup();
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
