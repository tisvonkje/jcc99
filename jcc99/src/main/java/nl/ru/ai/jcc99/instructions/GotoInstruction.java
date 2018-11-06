package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class GotoInstruction extends Instruction
{
  private int offset;
  private String label;

  public GotoInstruction(int position, Constant[] constants, int offset)
  {
    super(position,constants);
    this.offset=offset;
  }
  
  public String toString()
  {
    return String.format("goto %d",offset);
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeJump(label);
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    label=method.getLabel(classLoader,position+offset);
  }
}
