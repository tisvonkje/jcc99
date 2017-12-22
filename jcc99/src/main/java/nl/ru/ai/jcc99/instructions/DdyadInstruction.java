package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class DdyadInstruction extends Instruction
{
  private Operator operator;

  public DdyadInstruction(Constant[] constants, Operator operator)
  {
    super(constants);
    this.operator=operator;
  }
  
  public String toString()
  {
    return "d"+operator.toString().toLowerCase();
  }

}
