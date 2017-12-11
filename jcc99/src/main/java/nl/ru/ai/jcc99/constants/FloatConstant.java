package nl.ru.ai.jcc99.constants;

public class FloatConstant extends Constant
{
  private float value;

  public FloatConstant(Constant[] constants, float value)
  {
    super(constants);
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Float '%f'",value);
  }

}
