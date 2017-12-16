package nl.ru.ai.jcc99;

import java.io.Serializable;

/**
 * Hello world!
 *
 */
public class App extends Object implements Serializable, Comparable
{
  private int a;
  private int b;

  public App()
  {
    a=3;
    b=4;
  }
    /**
   * 
   */
  private static final long serialVersionUID=1L;

    public static void main( String[] args )
    {
        int [] blaat={0,1,2,3,4,1234567};
       double d=12.34;
//        float f=56.78F;
        System.out.println(blaat[2]);
    }

    public int compareTo(Object o)
    {
      // TODO Auto-generated method stub
      return 0;
    }
}
