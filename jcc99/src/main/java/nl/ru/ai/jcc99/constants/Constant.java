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
  
  protected Constant [] constants;

  public Constant(Constant[] constants)
  {
    this.constants=constants;
  }

  public static Constant create(Constant [] constants, ByteBuffer buffer)
  {
    byte tag=buffer.get();
    switch(tag)
    {
      case CONSTANT_CLASS:
        return new ClassConstant(constants,buffer.getShort());
      case CONSTANT_FIELDREF:
        return new FieldrefConstant(constants,buffer.getShort(),buffer.getShort());
      case CONSTANT_METHODREF:
        return new MethodrefConstant(constants,buffer.getShort(),buffer.getShort()); 
      case CONSTANT_INTERFACE_METHODREF:
        return new InterfaceMethodrefConstant(constants,buffer.getShort(),buffer.getShort());
      case CONSTANT_STRING:
        return new StringConstant(constants,buffer.getShort());
      case CONSTANT_INTEGER:
        return new IntegerConstant(constants,buffer.getInt());
      case CONSTANT_FLOAT:
        return new FloatConstant(constants,buffer.getFloat());
      case CONSTANT_LONG:
        return new LongConstant(constants,buffer.getLong());
      case CONSTANT_DOUBLE:
        return new DoubleConstant(constants,buffer.getDouble());
      case CONSTANT_NAME_AND_TYPE:
        return new NameAndTypeConstant(constants,buffer.getShort(),buffer.getShort());
      case CONTANT_UTF8:
        short length=buffer.getShort();
        byte [] bytes=new byte[length];
        buffer.get(bytes);
        return new UTF8Constant(constants,bytes);
      case CONSTANT_METHOD_HANDLE:
        return new MethodHandleConstant(constants,buffer.get(),buffer.getShort());
      case CONSTANT_METHOD_TYPE:
        return new MethodTypeConstant(constants,buffer.getShort());
      case CONSTANT_INVOKE_DYNAMIC:
        return new InvokeDynamicConstant(constants,buffer.getShort(),buffer.getShort());
      default:
        throw new RuntimeException(String.format("invalid tag '%d' in constant info",tag));
    }
  }

}
