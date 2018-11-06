package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;

public class AloadInstruction extends Instruction
{
  private TypeSuffix type; // Allowed: INT, LONG, FLOAT, DOUBLE, REF, BOOLEAN, CHAR, SHORT

  public AloadInstruction(int position, Constant[] constants, TypeSuffix type)
  {
    super(position,constants);
    this.type=type;
  }
  
  public String toString()
  {
    return String.format("%saload",type.toString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeArrayLoad(type);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
