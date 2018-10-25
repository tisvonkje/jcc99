package nl.ru.ai.jcc99;

public class Test
{
  public native static void print(int n);
  
  public static void main(String[] args)
  {
    int a=mul(3,4);
    print(a);
  }

  private static int mul(int i, int j)
  {
    return i*j;
  }

}
