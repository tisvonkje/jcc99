package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.constants.Constant;

public class LdcWInstruction extends Instruction
{
  private int index;

  public LdcWInstruction(Constant[] constants, int index)
  {
    super(constants);
    this.index=index;
  }
  
  public void analyze(ClassLoader loader)
  {
    
  }

  public String toString()
  {
    return String.format("ldc_w %d (%s)",index,constants[index].toShortString());
  }
}
