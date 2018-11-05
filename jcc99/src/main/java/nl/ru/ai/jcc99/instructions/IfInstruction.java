package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class IfInstruction extends Instruction
{
  private Condition condition;
  private short offset;;

  public IfInstruction(ByteBuffer buffer, Constant[] constants, Condition condition, short offset)
  {
    super(buffer,constants);
    this.condition=condition;
    this.offset=offset;
  }
  
  public String toString()
  {
    return "if"+condition.toString().toLowerCase()+" "+offset;
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
