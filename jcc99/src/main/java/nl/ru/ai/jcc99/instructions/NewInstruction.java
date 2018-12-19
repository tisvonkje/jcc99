package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassFile;
import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class NewInstruction extends Instruction
{
  private int classIndex;
  private ClassFile classFile;

  public NewInstruction(int position, Constant[] constants, int classIndex)
  {
    super(position,constants);
    this.classIndex=classIndex;
  }
  
  public String toString()
  {
    return String.format("new %d (%s)",classIndex,constants[classIndex].toShortString());
  }
  
  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    classFile=classLoader.getClassFile(constants[classIndex].toShortString());
    classFile.analyze();
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    int size=classFile.getSize();
    coder.codeAllocateObject(size,classFile);
  }
}
