package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class JsrInstruction extends Instruction
{
  private int offset;

  public JsrInstruction(Constant[] constants, int offset)
  {
    super(constants);
    this.offset=offset;
  }
  
  public String toString()
  {
    return String.format("jsr %d",offset);
  }

}
