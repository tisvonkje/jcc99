package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassFile;
import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Field;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.constants.FieldrefConstant;
import nl.ru.ai.jcc99.constants.NameAndTypeConstant;

public class PutfieldInstruction extends Instruction
{
  private int fieldIndex;

  public PutfieldInstruction(ByteBuffer buffer, Constant[] constants, int fieldIndex)
  {
    super(buffer,constants);
    this.fieldIndex=fieldIndex;
  }
  
  public String toString()
  {
    return String.format("putfield %d (%s)",fieldIndex,constants[fieldIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    FieldrefConstant constant=(FieldrefConstant)constants[fieldIndex];
    ClassFile classFile=classLoader.getClassFile(constants[constant.getclassIndex()].toShortString());
    NameAndTypeConstant nameAndType=(NameAndTypeConstant)constants[constant.getNameAndTypeIndex()];
    String fieldName=constants[nameAndType.getNameIndex()].toShortString();
    Field field=classFile.getField(fieldName);
    coder.codePutField(field.getOffset());
  }
}
