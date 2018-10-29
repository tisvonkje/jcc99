package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.constants.Constant;

//FIXME maybe merge all D.I,L,A-returnInstruction classes
public class IreturnInstruction extends Instruction
{

  public IreturnInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "ireturn";
  }
  
  public void code(int parameterUnits, Coder coder)
  {
    coder.codeReturnSingle(parameterUnits);
  }
}
