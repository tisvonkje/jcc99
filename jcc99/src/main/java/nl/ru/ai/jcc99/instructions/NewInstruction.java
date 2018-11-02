package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class NewInstruction extends Instruction
{
  private int classIndex;

  public NewInstruction(Constant[] constants, int classIndex)
  {
    super(constants);
    this.classIndex=classIndex;
  }
  
  public String toString()
  {
    return String.format("new %d (%s)",classIndex,constants[classIndex].toShortString());
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    throw new RuntimeException("notyet");
  }

}
