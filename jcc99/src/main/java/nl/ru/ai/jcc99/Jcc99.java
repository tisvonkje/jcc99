package nl.ru.ai.jcc99;

import java.io.File;
import java.io.IOException;

public class Jcc99
{

  public static void main(String[] args) throws IOException
  {
    Jcc99 compiler=new Jcc99();
    compiler.compile();
  }

  private void compile() throws IOException
  {
    ClassFile classFile=new ClassFile("target/classes","nl.ru.al.jc99.App");
    
  }

}
