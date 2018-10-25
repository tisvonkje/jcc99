package nl.ru.ai.jcc99;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Java class compiler
 * To test it, run it with the following arguments
 * -classpath target/classes nl.ru.ai.jcc99.Test
 * @author sparky
 */
public class Jcc99
{

  private CommandLine commandLine;

  public Jcc99(CommandLine commandLine)
  {
    this.commandLine=commandLine;
  }

  public static void main(String[] args) throws IOException
  {
    /*
    * Set options
    */
    Options options=new Options();
    options.addOption("classpath",true,"specify classpath (jars/dirs by ;)");
    options.addOption("h",false,"print this message");
    /*
    * Parse commandline
    */
    CommandLineParser parser=new DefaultParser();
    CommandLine commandLine;
    try
    {
      commandLine=parser.parse(options,args);
      /*
       * Handle help request
       */
      if(commandLine.hasOption('h'))
      {
        HelpFormatter formatter=new HelpFormatter();
        formatter.printHelp("jcc99 MainClass",options,true);
        System.exit(1);
      }
      /*
       * Check if there is an input file
       */
      int numberOfFiles=commandLine.getArgs().length;
      if(numberOfFiles==0)
      {
        System.out.println("Error: no class specified");
        System.exit(1);
      }
      if(numberOfFiles!=1)
      {
        System.out.println("Error: more than one class specified");
        System.exit(1);
      }
      /*
       * Create and run assembler
       */
      Jcc99 compiler=new Jcc99(commandLine);
      compiler.compile(commandLine.getArgs()[0]);
    }
    catch(ParseException e)
    {
      System.err.printf("Error parsing commandline '%s'\n",e.getMessage());
    }
  }

  private void compile(String className) throws IOException
  {
    /*
     * Get charpath
     */
    String [] charPath=commandLine.getOptionValue("classpath",".").split(";");
    /*
     * Load classes
     */
    ClassLoader classLoader=new ClassLoader(charPath);
    String methodName=className.replace('.','/')+".main:([Ljava/lang/String;)V";
    /*
     * Get static main method
     */
    Method mainMethod=classLoader.getStaticMethod(methodName);
    if(mainMethod==null)
    {
      System.err.printf("Error: cannot find main method '%s'\n",methodName);
      System.exit(1);
    }
    /*
     * Mark methods for coding (recursively)
     */
    mainMethod.markForCoding(classLoader);
    classLoader.dump();
  }
}
