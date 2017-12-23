package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Type;
import nl.ru.ai.jcc99.constants.Constant;

public class AstoreInstruction extends Instruction
{
  private Type type; // Allowed: INT, LONG, FLOAT, DOUBLE, REF, BOOLEAN, CHAR, SHORT

  public AstoreInstruction(Constant[] constants, Type type)
  {
    super(constants);
    this.type=type;
  }
  
  public String toString()
  {
    return String.format("%sastore",type.toString());
  }

}
