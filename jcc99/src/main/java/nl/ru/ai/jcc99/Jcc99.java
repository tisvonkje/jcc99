package nl.ru.ai.jcc99;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Java class compiler
 * Ideas behind compiler
 * - make it possible to generate code for 16 bit processors
 * - int -> 16 bit, long -> 32 bit
 * - float -> 16 bit, double ->32 bit
 * - char -> 16 bit, String are arrays of chars
 * Might introduce CStrings later
 * 
 * To test it, run it with the following arguments
 * -classpath ../../rts99/rts99/target/classes nl.ru.ai.rts99.App
 * It will use companion project rts99
 * 
 * @author sparky
 */
public class Jcc99
{

  private CommandLine commandLine;

  public Jcc99(CommandLine commandLine)
  {
    this.commandLine=commandLine;
  }

  public static void main(String[] args) throws IOException, NoSuchAlgorithmException
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

  private void compile(String className) throws IOException, NoSuchAlgorithmException
  {
    /*
     * Get charpath
     */
    String [] charPath=commandLine.getOptionValue("classpath",".").split(";");
    /*
     * Load classes
     */
    ClassLoader classLoader=new ClassLoader(charPath);
    String methodName=className.replace('.','/')+".main:([Ljava/lang/String;)I";
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
     * and collect constants
     */
    mainMethod.analyze(classLoader);
    if(Util.foundErrors())
    {
    	System.err.println("Errors found, bailing out");
    	System.exit(1);
    }
    /*	
     * Dump for now
     */
    classLoader.dump();
    /*
     * Generate code
     */
    PrintWriter writer=new PrintWriter(new File("output.s"));
    LabelDisambiguator disambiguator=new LabelDisambiguator();
    Coder coder=new Intel32MacOSXCoder(writer,disambiguator);
    classLoader.code(mainMethod,coder);
    writer.close();
  }
}
