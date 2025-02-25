package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassFile;
import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class InstanceofInstruction extends Instruction
{
  private int classIndex;

  public InstanceofInstruction(int position, Constant[] constants, int classIndex)
  {
    super(position,constants);
    this.classIndex=classIndex;
  }
  
  public String toString()
  {
    return String.format("instanceof %d (%s)",classIndex,constants[classIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  { 
    ClassFile classFile=classLoader.getClassFile(constants[classIndex].toShortString());
    coder.codeInstanceOf(classFile);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
