package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.PrimitiveType;
import nl.ru.ai.jcc99.constants.Constant;

public class NewarrayInstruction extends Instruction
{
  private PrimitiveType type;

  public NewarrayInstruction(Constant[] constants, PrimitiveType type)
  {
    super(constants);
    this.type=type;
  }
  
  public String toString()
  {
    return String.format("newarray %s",type.toString());
  }

}
