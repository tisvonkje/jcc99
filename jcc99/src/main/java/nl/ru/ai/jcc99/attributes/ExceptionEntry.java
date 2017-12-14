package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

public class ExceptionEntry
{
  private short startPC;
  private short endPC;
  private short handlerPC;
  private short catchType;

  public ExceptionEntry(ByteBuffer buffer)
  {
    startPC=buffer.getShort();
    endPC=buffer.getShort();
    handlerPC=buffer.getShort();
    catchType=buffer.getShort();
  }

}
