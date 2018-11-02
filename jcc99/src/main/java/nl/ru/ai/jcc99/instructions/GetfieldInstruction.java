package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class GetfieldInstruction extends Instruction
{
  private int fieldIndex;

  public GetfieldInstruction(Constant[] constants, int fieldIndex)
  {
    super(constants);
    this.fieldIndex=fieldIndex;
  }
  
  public String toString()
  {
    return String.format("getfield %d (%s)",fieldIndex,constants[fieldIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
