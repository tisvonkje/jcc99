package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FstoreInstruction extends Instruction
{
  private int local;

  public FstoreInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }
  
  public String toString()
  {
    return String.format("fstore %d",local);
  }

}
