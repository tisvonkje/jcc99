package nl.ru.ai.jcc99.types;

public interface Type
{
  /*
   * The following type id's are used for debugger symbols
   */
  public static int ARRAY_ID=1;
  public static int BOOLEAN_ID=2;
  public static int CHARACTER_ID=3;
  public static int CLASS_ID=4;
  public static int DOUBLE_ID=5;
  public static int FLOAT_ID=6;
  public static int INTEGER_ID=7;
  public static int LONG_ID=8;
  public static int METHOD_ID=9;
  public static int SHORT_ID=10;
  public static int VOID_ID=11;
  /*
   * Type API
   */
  int unitSize();
  int parameterUnitSize();
  int codeDebugId();
}
