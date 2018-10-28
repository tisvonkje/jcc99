package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;

public class StoreInstruction extends Instruction
{
  private TypeSuffix type; // Allowed: INT, LONG, FLOAT, DOUBLE, REF
  private int local;

  public StoreInstruction(Constant[] constants, TypeSuffix type, int local)
  {
    super(constants);
    this.type=type;
    this.local=local;
  }
  
  public String toString()
  {
    return String.format("%sstore %d",type.toString(),local);
  }
  
  public void code(int parameterUnits, Coder coder)
  {
    if(type.isSingle())
      coder.codeStore(parameterUnits,local);
    else
      coder.codeDStore(parameterUnits,local);
  }

}
