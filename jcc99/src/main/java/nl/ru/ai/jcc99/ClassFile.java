package nl.ru.ai.jcc99;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.constants.DoubleConstant;
import nl.ru.ai.jcc99.constants.LongConstant;

public class ClassFile
{
  /*
   * https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html
   * ClassFile 
   * {
   *   u4             magic;
   *   u2             minor_version;
   *   u2             major_version;
   *   u2             constant_pool_count;
   *   cp_info        constant_pool[constant_pool_count-1];
   *   u2             access_flags;
   *   u2             this_class;
   *   u2             super_class;
   *   u2             interfaces_count;
   *   u2             interfaces[interfaces_count];
   *   u2             fields_count;
   *   field_info     fields[fields_count];
   *   u2             methods_count;
   *   method_info    methods[methods_count];
   *   u2             attributes_count;
   *   attribute_info attributes[attributes_count];
   * }
   */
  private int magic;
  private short minor;
  private short major;
  private short constantPoolCount;
  /*
   * constants, note that some slots may be unused (null)
   */
  private Constant[] constants;
  /*
   * Constructor
   */
  public ClassFile(String folderName, String classFileName) throws IOException
  {
    File folder=new File(folderName);
    File file=new File(folderName,classFileName.replace(".","/")+".class");
    FileInputStream inputStream=new FileInputStream(file);
    FileChannel channel=inputStream.getChannel();
    ByteBuffer buffer=ByteBuffer.allocate((int)file.length());
    channel.read(buffer);
    buffer.flip();
    magic=buffer.getInt();
    System.out.printf("%04x\n",magic);
    minor=buffer.getShort();
    major=buffer.getShort();
    System.out.printf("major=%d, minor=%d\n",major,minor);
    if(major!=49 || minor!=0)
      throw new RuntimeException("Invalid class file version");
    constantPoolCount=buffer.getShort();
    System.out.printf("constant pool count=%d\n",constantPoolCount);
    constants=new Constant[constantPoolCount];
    for(int i=1;i<constantPoolCount;i++)
    {
      constants[i]=Constant.create(buffer);
      System.out.printf("%d: %s\n",i,constants[i]);
      /*
       * We have to skip a constant slot for Long and Double constants
       * From JVM spec: In retrospect, making 8-byte constants take two constant pool entries was a poor choice.
       * I agree, but who ever thought it was a good idea? UTF8 slots have fixed length so why?
       */
      if(constants[i] instanceof LongConstant || constants[i] instanceof DoubleConstant)
        i++;
    }
  }

}
