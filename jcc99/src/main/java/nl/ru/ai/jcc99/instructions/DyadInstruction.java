package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;

public class DyadInstruction extends Instruction
{
  /*
   * For INT    allowed: ADD, SUB, MUL, DIV, REM, SHL, SHR, USHR, AND, OR, XOR
   * For LONG   allowed: ADD, SUB, MUL, DIV, REM, SHL, SHR, USHR, AND, OR, XOR
   * For FLOAT  allowed: ADD, SUB, MUL, DIV, REM
   * for DOUBLE allowed: ADD, SUB. MUL, DIV, REM
   */
  private TypeSuffix type;
  private Operator operator;

  public DyadInstruction(Constant[] constants, TypeSuffix type, Operator operator)
  {
    super(constants);
    this.type=type;
    this.operator=operator;
  }
  
  public String toString()
  {
    return type.toString()+operator.toString().toLowerCase();
  }
  
  public void code(int parameterUnits, Coder coder)
  {
    if(type==TypeSuffix.INT && operator==Operator.ADD)
      coder.codeAddInt();
    else
      throw new RuntimeException("notyet");
  }
}
