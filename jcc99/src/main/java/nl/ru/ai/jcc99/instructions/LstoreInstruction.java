package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LstoreInstruction extends Instruction
{

  private int local;

  public LstoreInstruction(Constant[] constants, int local)
  {
    super(constants);
    this.local=local;
  }
  
  public String toString()
  {
    return String.format("lstore %d",local);
  }

}
