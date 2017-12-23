package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.constants.Constant;

public class Multianewarray extends Instruction
{

  private int classIndex;
  private int dimensions;

  public Multianewarray(Constant[] constants, int classIndex, int dimensions)
  {
    super(constants);
    this.classIndex=classIndex;
    this.dimensions=dimensions;
  }
  
  public String toString()
  {
    return String.format("multianewarray %d (%s),%d",classIndex,constants[classIndex].toShortString(),dimensions);
  }

}
