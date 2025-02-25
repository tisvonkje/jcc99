package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;

public class AnewarrayInstruction extends Instruction
{
  private int componentTypeIndex;

  public AnewarrayInstruction(int position, Constant[] constants, int componentTypeIndex)
  {
    super(position,constants);
    this.componentTypeIndex=componentTypeIndex;
  }
  
  public String toString()
  {
    return String.format("anewarray %d (%s)",componentTypeIndex,constants[componentTypeIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeAllocateArray(TypeSuffix.REF);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
