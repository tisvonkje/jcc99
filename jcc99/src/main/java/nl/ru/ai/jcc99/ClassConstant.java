package nl.ru.ai.jcc99;

import java.nio.ByteBuffer;

public class ClassConstant
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

  public ClassConstant(ByteBuffer buffer)
  {
    byte tag=buffer.get();
    switch(tag)
    {
      case CONSTANT_CLASS:
        short nameIndex=buffer.getShort();
        System.out.printf("%d: Class name=%d\n",i,nameIndex);
        break;
      case CONSTANT_FIELDREF:
        nameIndex=buffer.getShort();
        short nameAndTypeIndex=buffer.getShort();
        System.out.printf("%d: Fieldref name=%d name and type=%d\n",i,nameIndex,nameAndTypeIndex);
        break;
      case CONSTANT_METHODREF:
        nameIndex=buffer.getShort();
        nameAndTypeIndex=buffer.getShort();
        System.out.printf("%d: Methodref name=%d name and type=%d\n",i,nameIndex,nameAndTypeIndex);
        break;
      case CONSTANT_INTERFACE_METHODREF:
        nameIndex=buffer.getShort();
        nameAndTypeIndex=buffer.getShort();
        System.out.printf("%d: Interface Methodref name=%d name and type=%d\n",i,nameIndex,nameAndTypeIndex);
        break;
      case CONSTANT_STRING:
        short stringIndex=buffer.getShort();
        System.out.printf("%d: String index=%d\n",i,stringIndex);
        break;
      case CONSTANT_INTEGER:
        int integer=buffer.getInt();
        System.out.printf("%d: Integer value=%d\n",i,integer);
        break;
      case CONSTANT_FLOAT:
        float f=buffer.getFloat();
        System.out.printf("%d: Float value=%f\n",i,f);
        break;
      case CONSTANT_LONG:
        long l=buffer.getLong();
        System.out.printf("%d: Long value=%ld\n",i,l);
        break;
      case CONSTANT_DOUBLE:
        double d=buffer.getDouble();
        System.out.printf("%d: Double value=%lf\n",i,d);
        break;
      case CONSTANT_NAME_AND_TYPE:
        nameIndex=buffer.getShort();
        int decriptorIndex=buffer.getShort();
        System.out.printf("%d: Name and type name=%d name and type=%d\n",i,nameIndex,decriptorIndex);
        break;
      case CONTANT_UTF8:
        short length=buffer.getShort();
        byte [] b=new byte[length];
        buffer.get(b);
        String str=new String(b);
        System.out.printf("%d: UTF8 %d bytes: %s\n",i,length,str);
        break;
      case CONSTANT_METHOD_HANDLE:
      case CONSTANT_METHOD_TYPE:
      case CONSTANT_INVOKE_DYNAMIC:
        break;
      default:
        throw new RuntimeException("invalid tag in constant info");
    }
  }

}
