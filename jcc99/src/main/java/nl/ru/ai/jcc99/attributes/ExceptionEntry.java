package nl.ru.ai.jcc99.attributes;

import java.nio.ByteBuffer;

public class ExceptionEntry
{
  @SuppressWarnings("unused")
  private short startPC;
  @SuppressWarnings("unused")
  private short endPC;
  @SuppressWarnings("unused")
  private short handlerPC;
  @SuppressWarnings("unused")
  private short catchType;

  public ExceptionEntry(ByteBuffer buffer)
  {
    startPC=buffer.getShort();
    endPC=buffer.getShort();
    handlerPC=buffer.getShort();
    catchType=buffer.getShort();
  }

}
