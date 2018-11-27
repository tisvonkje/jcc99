package nl.ru.ai.jcc.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import nl.ru.ai.jcc99.Jcc99;

public class Regression
{
  private static final String BASE="../../rts99/rts99";
  private static final String SOURCE="src/main/java";
  private static final String CLASS="target/classes";
  private static final String PACKAGE="nl.ru.ai.jcc.regression";

  /**
   * Run the regression test for the class compiler
   * @param args
   */
  public static void main(String[] args)
  {
    new Regression().go(BASE);
  }

  private void go(String path)
  {
    File base=new File(path);
    File sourcePath=new File(base,String.format("%s/%s",SOURCE,PACKAGE.replace('.','/')));
    for(File file : sourcePath.listFiles())
    {
      //-classpath ../../rts99/rts99/target/classes nl.ru.ai.jcc.regression.HelloWorld
      String name=file.getName();
      if(name.endsWith(".java"))
      {
        name=name.substring(0,name.length()-5);
        try
        {
          /*
           * Set up commandline for jcc
           */
          String[] jccArgs=new String[3];
          jccArgs[0]="-classpath";
          jccArgs[1]=String.format("%s/%s",BASE,CLASS);
          jccArgs[2]=String.format("%s.%s",PACKAGE,name);
          /*
           * Print commandline
           */
          System.out.printf("jcc99 ");
          for(int i=0;i<jccArgs.length;i++)
            System.out.print(jccArgs[i]+" ");
          System.out.println();
          /*
           * Run compiler
           */
          Jcc99.main(jccArgs);
          if(!new File("output.s").exists())
            throw new RuntimeException("no output file");
          /*
           * Run assembler
           */
          String assemblerCommand="gcc -fno-pie -m32 output.s rts.s entry.c";
          System.out.println(assemblerCommand);
          CommandLine assemblerLine=CommandLine.parse(assemblerCommand);
          DefaultExecutor executor=new DefaultExecutor();
          int exitValue=executor.execute(assemblerLine);
          if(exitValue!=0)
          {
            System.err.println("Assembly phase failed");
            System.exit(1);
          }
          /*
           * Run the executable
           */
          String command="./a.out";
          File folder=new File(BASE,String.format("regression/%s",name));
          File args=new File(folder,"args");
          if(args.exists())
            command=command+" "+readSingleLine(args);
          System.out.println(command);
          CommandLine commandLine=CommandLine.parse(command);
          executor=new DefaultExecutor();
          exitValue=executor.execute(commandLine);
          System.out.println(exitValue);
        }
        catch(Exception e)
        {
          System.out.printf("Compilation failed because of Exception '%s'\n",e.getMessage());
          System.exit(1);
        }
      }
    }
  }

  private String readSingleLine(File file) throws IOException
  {
    BufferedReader fileReader=null;
    try
    {
      fileReader=new BufferedReader(new FileReader(file));
      return fileReader.readLine();
    } finally
    {
      if(fileReader!=null)
        fileReader.close();
    }
  }

}
