package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DconstInstruction extends Instruction
{
  private double value;

  public DconstInstruction(Constant[] constants, double value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("dconst %f",value);
  }

}
