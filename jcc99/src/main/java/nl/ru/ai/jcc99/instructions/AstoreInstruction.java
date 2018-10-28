package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;

public class AstoreInstruction extends Instruction
{
  private TypeSuffix type; // Allowed: INT, LONG, FLOAT, DOUBLE, REF, BOOLEAN, CHAR, SHORT

  public AstoreInstruction(Constant[] constants, TypeSuffix type)
  {
    super(constants);
    this.type=type;
  }
  
  public String toString()
  {
    return String.format("%sastore",type.toString());
  }

}
