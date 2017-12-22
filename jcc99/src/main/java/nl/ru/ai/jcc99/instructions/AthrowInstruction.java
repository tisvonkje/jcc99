package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class AthrowInstruction extends Instruction
{

  public AthrowInstruction(Constant[] constants)
  {
    super(constants);
  }

  public String toString()
  {
    return "athrow";
  }
}
