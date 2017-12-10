package nl.ru.ai.jcc99.constants;

import java.nio.ByteBuffer;

public class Constant
{
  private static final byte CONSTANT_CLASS=7;
  private static final byte CONSTANT_FIELDREF=9;
  private static final byte CONSTANT_METHODREF=10;
  private static final byte CONSTANT_INTERFACE_METHODREF=11;
  private static final byte CONSTANT_STRING=8;
  private static final byte CONSTANT_INTEGER=3;
  private static final byte CONSTANT_FLOAT=4;
  private static final byte CONSTANT_LONG=5;
  private static final byte CONSTANT_DOUBLE=6;
  private static final byte CONSTANT_NAME_AND_TYPE=12;
  private static final byte CONTANT_UTF8=1;
  private static final byte CONSTANT_METHOD_HANDLE=15;
  private static final byte CONSTANT_METHOD_TYPE=16;
  private static final byte CONSTANT_INVOKE_DYNAMIC=18;

  public static Constant create(ByteBuffer buffer)
  {
    byte tag=buffer.get();
    switch(tag)
    {
      case CONSTANT_CLASS:
        return new ClassConstant(buffer.getShort());
      case CONSTANT_FIELDREF:
        return new FieldrefConstant(buffer.getShort(),buffer.getShort());
      case CONSTANT_METHODREF:
        return new MethodrefConstant(buffer.getShort(),buffer.getShort()); 
      case CONSTANT_INTERFACE_METHODREF:
        return new InterfaceMethodrefConstant(buffer.getShort(),buffer.getShort());
      case CONSTANT_STRING:
        return new StringConstant(buffer.getShort());
      case CONSTANT_INTEGER:
        return new IntegerConstant(buffer.getInt());
      case CONSTANT_FLOAT:
        return new FloatConstant(buffer.getFloat());
      case CONSTANT_LONG:
        return new LongConstant(buffer.getLong());
      case CONSTANT_DOUBLE:
        return new DoubleConstant(buffer.getDouble());
      case CONSTANT_NAME_AND_TYPE:
        return new NameAndTypeConstant(buffer.getShort(),buffer.getShort());
      case CONTANT_UTF8:
        short length=buffer.getShort();
        byte [] bytes=new byte[length];
        buffer.get(bytes);
        return new UTF8Constant(bytes);
      case CONSTANT_METHOD_HANDLE:
        return new MethodHandleConstant(buffer.get(),buffer.getShort());
      case CONSTANT_METHOD_TYPE:
        return new MethodTypeConstant(buffer.getShort());
      case CONSTANT_INVOKE_DYNAMIC:
        return new InvokeDynamicConstant(buffer.getShort(),buffer.getShort());
      default:
        throw new RuntimeException(String.format("invalid tag '%d' in constant info",tag));
    }
  }

}
