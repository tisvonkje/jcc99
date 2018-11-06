package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class ConvertInstruction extends Instruction
{
  private Conversion conversion;

  public ConvertInstruction(int position, Constant[] constants, Conversion conversion)
  {
    super(position,constants);
    this.conversion=conversion;
  }

  public String toString()
  {
    return conversion.toString().toLowerCase();
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    switch(conversion)
    {
      case I2B:
        coder.codeIntToByte();
        break;
      default:
        throw new RuntimeException("notyet");
    }
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub

  }
}
