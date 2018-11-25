package nl.ru.ai.jcc.test;

import java.io.File;

public class Regression
{
  private static final String BASE="../../rts99/rts99";

  /**
   * Run the regression test for the class compiler
   * @param args
   */
  public static void main(String [] args)
  {
    new Regression().go(BASE);
  }

  private void go(String path)
  {
    File base=new File(path);
    File sourcePath=new File(base,"src/main/java/nl/ru/ai/jcc/regression");
    for(File file:sourcePath.listFiles())
      System.out.println(file);
  }

}
