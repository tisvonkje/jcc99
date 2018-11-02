package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassFile;
import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.Util;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.constants.FieldrefConstant;

public class PutstaticInstruction extends Instruction
{
  private int fieldIndex;

  public PutstaticInstruction(Constant[] constants, int fieldIndex)
  {
    super(constants);
    this.fieldIndex=fieldIndex;
  }
  
  public void analyze(ClassLoader classLoader)
  {
    FieldrefConstant fieldrefConstant=(FieldrefConstant)constants[fieldIndex];
    String className=fieldrefConstant.getClassName();
    ClassFile classFile=classLoader.getClassFile(className);
    if(classFile==null)
      Util.error("Cannot mark '%s' for coding",className);
    else
      classFile.analyze(classLoader);
  }
  
  public String toString()
  {
    return String.format("putstatic %d (%s)",fieldIndex,constants[fieldIndex].toShortString());
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
