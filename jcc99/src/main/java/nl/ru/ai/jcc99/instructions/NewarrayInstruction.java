package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Type;
import nl.ru.ai.jcc99.constants.Constant;

public class NewarrayInstruction extends Instruction
{
  private Type type;

  public NewarrayInstruction(Constant[] constants, Type type)
  {
    super(constants);
    this.type=type;
  }
  
  public String toString()
  {
    return String.format("newarray %s",type.toString());
  }

}
