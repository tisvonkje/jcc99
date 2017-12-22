package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class IdyadInstruction extends Instruction
{
  private Operator operator;

  public IdyadInstruction(Constant[] constants, Operator operator)
  {
    super(constants);
    this.operator=operator;
  }
  
  public String toString()
  {
    return "l"+operator.toString().toLowerCase();
  }

}
