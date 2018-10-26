package nl.ru.ai.jcc99;

import java.io.PrintWriter;

public class Intel32MacOSXCoder implements Coder
{
  private PrintWriter writer;
  private LabelDisambiguator disambiguator;

  public Intel32MacOSXCoder(PrintWriter writer, LabelDisambiguator disambiguator)
  {
    this.writer=writer;
    this.disambiguator=disambiguator;
  }

  public void codeEntry(Method method)
  { 
    writer.printf("\t.globl\t _main\n");
    writer.printf("_main:\tjmp\t%s\n",disambiguator.name(method));
  }

  public void codeLabel(Method method)
  {
    writer.printf("%s:\n",disambiguator.name(method));
  }

  public String getVersion()
  {
    return "MacOSX intel 32 bit coder";
  }

  public void codeComment(String comment)
  {
    writer.printf("# %s\n",comment);
  }

}
