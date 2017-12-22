package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class FdyadInstruction extends Instruction
{
  private Operator operator;

  public FdyadInstruction(Constant[] constants, Operator operator)
  {
    super(constants);
    this.operator=operator;
  }
  
  public String toString()
  {
    return "f"+operator.toString().toLowerCase();
  }

}
