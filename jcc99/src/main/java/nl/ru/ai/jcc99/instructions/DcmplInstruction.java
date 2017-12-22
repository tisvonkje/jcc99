package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DcmplInstruction extends Instruction
{

  public DcmplInstruction(Constant[] constants)
  {
    super(constants);
  }
  
  public String toString()
  {
    return "dcmpl";
  }

}
