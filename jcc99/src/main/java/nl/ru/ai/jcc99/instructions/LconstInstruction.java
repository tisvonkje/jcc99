package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LconstInstruction extends Instruction
{
  private long value;

  public LconstInstruction(Constant[] constants, long value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("lconst %d",value);
  }

}
