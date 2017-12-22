package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class LdyadInstruction extends Instruction
{
  private Operator operator;

  public LdyadInstruction(Constant[] constants, Operator operator)
  {
    super(constants);
    this.operator=operator;
  }
  
  public String toString()
  {
    return "i"+operator.toString().toLowerCase();
  }

}
