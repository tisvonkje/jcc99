package nl.ru.ai.jcc99.constants;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;

/**
 * Inline constants can be generated as immediate operands, so are not collected into
 * the constant pool. This is why they do not need a special analyze method
 * @author sparky
 *
 */
public abstract class InlineConstant extends Constant
{
  public InlineConstant(Constant[] constants)
  {
    super(constants);
  }

  @Override
  public void analyze(ClassLoader loader)
  {
    // inline constants do not need analyze
  }
  
  /**
   * Default implementation: give an error (should not occur)
   * @param coder
   */
  public void codeLoad(Coder coder)
  {
    coder.close();
    throw new RuntimeException("don't know how to codeLoad "+getClass());
  }
}
