package nl.ru.ai.jcc.test;

import org.apache.commons.exec.DefaultExecutor;

public class MyExecutor extends DefaultExecutor
{
  public boolean isFailure(final int exitValue)
  {
    return false;
  }
}
