package nl.ru.ai.jcc99.constants;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;

/**
 * Outline constants should be collected into the constant pool
 * They will get a label number attached
 * @author sparky
 *
 */
public abstract class OutlineConstant extends Constant
{
  private Integer labelNumber;

  public OutlineConstant(Constant[] constants)
  {
    super(constants);
  }

  @Override
  public void analyze(ClassLoader loader)
  {
    if(labelNumber==null)
     labelNumber=loader.addConstant(this);
  }

  public void codeLoad(Coder coder)
  {
    coder.codePushAddress(this);
  }

  public String getLabel()
  {
    return String.format("constant%d",labelNumber);
  }

  public void code(Coder coder)
  {
    coder.codeAlignWord();
    coder.codeLabel(this);
  }
}
