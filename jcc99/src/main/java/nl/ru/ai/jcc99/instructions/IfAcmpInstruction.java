package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class IfAcmpInstruction extends Instruction
{
  private Condition condition;
  private short offset;

  public IfAcmpInstruction(Constant[] constants, Condition condition, short offset)
  {
    super(constants);
    this.condition=condition;
    this.offset=offset;
  }
  
  public String toString()
  {
    return "if_acmp"+condition.toString().toLowerCase()+" "+offset;
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to code "+getClass());
  }
}
