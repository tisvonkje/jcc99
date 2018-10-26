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

  public void codeEntry(String label)
  { 
    writer.printf("\t.entry\t%s\n",disambiguator.disambiguate(label));
  }

  public void codeLabel(String label)
  {
    writer.printf("%s:\n",label);
  }

}
