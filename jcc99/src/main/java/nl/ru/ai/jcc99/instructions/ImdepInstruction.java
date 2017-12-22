package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class ImdepInstruction extends Instruction
{

  private int value;

  public ImdepInstruction(Constant[] constants, int value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("imdep %d",value);
  }

}
