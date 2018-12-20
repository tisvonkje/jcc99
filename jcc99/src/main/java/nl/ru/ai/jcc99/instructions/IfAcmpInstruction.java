package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class IfAcmpInstruction extends Instruction
{
  private Condition condition;
  private short offset;
  private String label;

  public IfAcmpInstruction(int position, Constant[] constants, Condition condition, short offset)
  {
    super(position,constants);
    this.condition=condition;
    this.offset=offset;
  }
  
  public String toString()
  {
    return "if_acmp"+condition.toString().toLowerCase()+" "+offset;
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeRefCompare(condition,label);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    label=method.getLabel(classLoader,position+offset);
  }
}
