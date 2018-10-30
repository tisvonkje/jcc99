package nl.ru.ai.jcc99;

public class Test
{
  public native static void print(String string);
  
  public static void main(String[] args)
  {
    char [] test=new char[2];
    test[0]='A';
    test[1]='B';
    String s=new String(test);
  }
}
