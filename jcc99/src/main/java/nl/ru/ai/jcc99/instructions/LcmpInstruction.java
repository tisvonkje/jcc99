package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LcmpInstruction extends Instruction
{

  public LcmpInstruction(Constant[] constants)
  {
    super(constants);
  }

  public String toString()
  {
    return "lcmp";
  }

}
