package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DloadInstruction extends Instruction
{
  private int local;

  public DloadInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }
  
  public String toString()
  {
    return String.format("dload %d",local);
  }

}
