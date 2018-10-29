package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.constants.Constant;

public class ReturnInstruction extends Instruction
{

  public ReturnInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "return";
  }
  
  public void code(int parameterUnits, Coder coder)
  {
    coder.codeReturn();
  }

}
