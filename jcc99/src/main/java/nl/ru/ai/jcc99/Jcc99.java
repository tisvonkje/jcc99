package nl.ru.ai.jcc99;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Java class compiler
 * @author sparky
 */
public class Jcc99
{

  public static void main(String[] args) throws IOException
  {
    /*
    * Set options
    */
    Options options=new Options();
    options.addOption("-classpath",true,"specify classpath (seperated by ;)");
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
        formatter.printHelp("jcc99 Class.class",options,true);
        System.exit(1);
      }
      /*
       * Check if there is an input file
       */
      int numberOfFiles=commandLine.getArgs().length;
      if(numberOfFiles==0)
      {
        System.out.println("Error: no input file specified");
        System.exit(1);
      }
      if(numberOfFiles!=1)
      {
        System.out.println("Error: more than one input file specified");
        System.exit(1);
      }
      /*
       * Create and run assembler
       */
      Jcc99 compiler=new Jcc99();
      compiler.compile(commandLine.getArgs()[0]);
    }
    catch(ParseException e)
    {
      System.err.printf("Error parsing commandline '%s'\n",e.getMessage());
    }
  }

  private void compile(String classFileName) throws IOException
  {
    ClassFile classFile=new ClassFile("target/classes","nl.ru.ai.jcc99.App");

  }

}
