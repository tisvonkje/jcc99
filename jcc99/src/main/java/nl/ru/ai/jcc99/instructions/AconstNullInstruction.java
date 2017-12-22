package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class AconstNullInstruction extends Instruction
{

  public AconstNullInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "aconst_null";
  }

}
