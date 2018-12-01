package nl.ru.ai.jcc.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.PumpStreamHandler;

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
           * Set up arguments if needed
           */
          String command="./a.out";
          File folder=new File(BASE,String.format("regression/%s",name));
          File args=new File(folder,"args");
          if(args.exists())
            command=command+" "+readSingleLine(args);
          System.out.println(command);
          CommandLine commandLine=CommandLine.parse(command);
          /*
           * Set up stdin if needed
           */
          FileInputStream stdin=null;
          File stdinFile=new File(folder,"stdin");
          if(stdinFile.exists())
            stdin=new FileInputStream(stdinFile);
          /*
           * Create executor
           */
          executor=new MyExecutor();
          ByteArrayOutputStream stdout=new ByteArrayOutputStream();
          ByteArrayOutputStream stderr=new ByteArrayOutputStream();
          PumpStreamHandler pumpStreamHandler=new PumpStreamHandler(stdout,stderr,stdin);
          executor.setStreamHandler(pumpStreamHandler);
          exitValue=executor.execute(commandLine);
          /*
           * Get expected exit value
           */
          int expectedExitValue=0;
          File rcFile=new File(folder,"rc");
          if(rcFile.exists())
            expectedExitValue=Integer.parseInt(readSingleLine(rcFile));
          if(expectedExitValue!=exitValue)
          {
            System.out.printf("Error: test returned %d, expected %d\n",exitValue,expectedExitValue);
            System.exit(1);
          }
          /*
           * If stdout file is present, check content
           */
          File stdoutFile=new File(folder,"stdout");
          if(stdoutFile.exists())
            fileCheck(name,stdoutFile,stdout.toByteArray());
          /*
           * If stderr file is present, check content
           */
          File stderrFile=new File(folder,"stderr");
          if(stderrFile.exists())
            fileCheck(name,stderrFile,stderr.toByteArray());
        }
        catch(Exception e)
        {
          e.printStackTrace();
          System.out.printf("Compilation failed because of Exception '%s'\n",e.getMessage());
          System.exit(1);
        }
      }
    }
  }
  
  private void fileCheck(String name,File file, byte [] content) throws IOException
  {
    File errorFile=new File(file.getParentFile(),file.getName()+".err");
    errorFile.delete();
    FileInputStream inputStream=new FileInputStream(file);
    int c;
    int i=0;
    while((c=inputStream.read())>=0)
    {
      if(c!=(content[i]&0xff))
      {
        System.out.printf("Error: '%s' created incorrect '%s', writing it to '%s.err'\n",name,file.getName(),file.getName());
        FileOutputStream output=new FileOutputStream(errorFile);
        output.write(content);
        output.close();
        System.exit(1);
      }
      i++;
    }
    if(i!=content.length)
    {
      System.out.printf("Error: '%s' created incorrect '%s', writing it to '%s.err'\n",name,file.getName(),file.getName());
      FileOutputStream output=new FileOutputStream(errorFile);
      output.write(content);
      output.close();
      System.exit(1);
    }
    inputStream.close();
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
