package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Type;
import nl.ru.ai.jcc99.constants.Constant;

public class StoreInstruction extends Instruction
{
  private Type type; // Allowed: INT, LONG, FLOAT, DOUBLE, REF
  private int local;

  public StoreInstruction(Constant[] constants, Type type, int local)
  {
    super(constants);
    this.type=type;
    this.local=local;
  }
  
  public String toString()
  {
    return String.format("%sstore %d",type.toString(),local);
  }

}
