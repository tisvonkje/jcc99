package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class IfInstruction extends Instruction
{
  private Condition condition;
  private short offset;
  private String label;;

  public IfInstruction(int position, Constant[] constants, Condition condition, short offset)
  {
    super(position,constants);
    this.condition=condition;
    this.offset=offset;
  }
  
  public String toString()
  {
    return "if"+condition.toString().toLowerCase()+" "+offset;
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeZeroCompare(condition,label);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    label=method.getLabel(classLoader,position+offset);
  }
}
