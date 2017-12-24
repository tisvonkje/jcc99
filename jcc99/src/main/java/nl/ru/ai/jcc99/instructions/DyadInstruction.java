package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Type;
import nl.ru.ai.jcc99.constants.Constant;

public class DyadInstruction extends Instruction
{
  /*
   * For INT    allowed: ADD, SUB, MUL, DIV, REM, SHL, SHR, USHR, AND, OR, XOR
   * For LONG   allowed: ADD, SUB, MUL, DIV, REM, SHL, SHR, USHR, AND, OR, XOR
   * For FLOAT  allowed: ADD, SUB, MUL, DIV, REM
   * for DOUBLE allowed: ADD, SUB. MUL, DIV, REM
   */
  private Type type;
  private Operator operator;

  public DyadInstruction(Constant[] constants, Type type, Operator operator)
  {
    super(constants);
    this.type=type;
    this.operator=operator;
  }
  
  public String toString()
  {
    return type.toString()+operator.toString().toLowerCase();
  }
}
