package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class CastoreInstruction extends Instruction
{

  public CastoreInstruction(Constant[] constants)
  {
    super(constants);
  }

  public String toString()
  {
    return "castore";
  }
}
