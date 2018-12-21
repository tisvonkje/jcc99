package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.constants.Constant;

public class ExceptionEntry
{
  private Constant[] constants;
  private short startPC;
  private String startLabel;
  private short endPC;
  private String endLabel;
  private short handlerPC;
  private String handlerLabel;
  private short catchType;

  public ExceptionEntry(Constant[] constants, ByteBuffer buffer)
  {
    this.constants=constants;
    startPC=buffer.getShort();
    endPC=buffer.getShort();
    handlerPC=buffer.getShort();
    catchType=buffer.getShort();
  }
  
  public String toString()
  {
    return String.format("from %d to %d target %d for %s",startPC,endPC,handlerPC,constants[catchType].toShortString());
  }

  public void analyze(ClassLoader classLoader, Method method)
  {
    startLabel=method.getLabel(classLoader,startPC);
    endLabel=method.getLabel(classLoader,endPC);
    handlerLabel=method.getLabel(classLoader,handlerPC);
  }

}
