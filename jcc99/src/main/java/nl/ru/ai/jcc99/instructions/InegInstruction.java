package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class InegInstruction extends Instruction
{

  public InegInstruction(int position, Constant[] constants)
  {
    super(position,constants);
  }
  
  public String toString()
  {
    return "ineg";
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
   coder.codeNegInt();
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
