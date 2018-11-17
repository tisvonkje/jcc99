package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
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

  public DyadInstruction(int position, Constant[] constants, TypeSuffix type, Operator operator)
  {
    super(position,constants);
    this.type=type;
    this.operator=operator;
  }
  
  public String toString()
  {
    return type.toString()+operator.toString().toLowerCase();
  }
  
  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    if(type==TypeSuffix.INT && operator==Operator.ADD)
      coder.codeAddInt();
    else if(type==TypeSuffix.INT && operator==Operator.SUB)
      coder.codeSubInt();
    else if(type==TypeSuffix.INT && operator==Operator.AND)
      coder.codeAndInt();
    else if(type==TypeSuffix.INT && operator==Operator.OR)
      coder.codeOrInt();
    else if(type==TypeSuffix.INT && operator==Operator.SHR)
      coder.codeShiftRightInt();
    else
      throw new RuntimeException("notyet '"+type+'-'+operator+"'");
  }

  @Override
  public void analyze(ClassLoader classLoader, Method method)
  {
    // TODO Auto-generated method stub
    
  }
}
