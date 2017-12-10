package nl.ru.ai.jcc99.constants;

public class FloatConstant extends Constant
{
  private float value;

  public FloatConstant(float value)
  {
    this.value=value;
  }
  
  public String toString()
  {
    return String.format("Float '%f'",value);
  }

}
