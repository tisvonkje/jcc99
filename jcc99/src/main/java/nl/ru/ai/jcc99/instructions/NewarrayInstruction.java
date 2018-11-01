package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;

public class NewarrayInstruction extends Instruction
{
  private TypeSuffix type;

  public NewarrayInstruction(Constant[] constants, TypeSuffix type)
  {
    super(constants);
    this.type=type;
  }
  
  public String toString()
  {
    return String.format("newarray %s",type.toString());
  }
  
  // number of elements on stack
  public void code(int parameterUnits, Coder coder)
  {
    coder.codeAllocate(coder.getRealSize(type));
  }

}
