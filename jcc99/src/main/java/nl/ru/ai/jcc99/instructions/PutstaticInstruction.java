package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassFile;
import nl.ru.ai.jcc99.ClassLoader;
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
  
  public void markForCoding(ClassLoader classLoader)
  {
    FieldrefConstant fieldrefConstant=(FieldrefConstant)constants[fieldIndex];
    String className=fieldrefConstant.getClassName();
    ClassFile classFile=classLoader.getClassFile(className);
    classFile.markForCoding(classLoader);
  }
  
  public String toString()
  {
    return String.format("putstatic %d (%s)",fieldIndex,constants[fieldIndex].toShortString());
  }

}
