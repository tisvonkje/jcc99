package nl.ru.ai.jcc99.constants;

public class MethodHandleConstant extends Constant
{
  private byte referenceKind;
  private short referenceIndex;

  public MethodHandleConstant(Constant[] constants, byte referenceKind, short referenceIndex)
  {
    super(constants);
    this.referenceKind=referenceKind;
    this.referenceIndex=referenceIndex;
  }
  
  public String toString()
  {
    return String.format("MethodHandle #%d #%d // %s",referenceKind,referenceIndex,toShortString());
  }

  @Override
  public String toShortString()
  {
    return String.format("%d %s",referenceKind,constants[referenceIndex].toShortString());
  }

}
