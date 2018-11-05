package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;

public class StoreInstruction extends Instruction
{
  private TypeSuffix type; // Allowed: INT, LONG, FLOAT, DOUBLE, REF
  private int local;

  public StoreInstruction(ByteBuffer buffer, Constant[] constants, TypeSuffix type, int local)
  {
    super(buffer,constants);
    this.type=type;
    this.local=local;
  }
  
  public String toString()
  {
    return String.format("%sstore %d",type.toString(),local);
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    if(type.isSingle())
      coder.codeStore(method.getParameterUnits(),local);
    else
      coder.codeDStore(method.getParameterUnits(),local);
  }

}
