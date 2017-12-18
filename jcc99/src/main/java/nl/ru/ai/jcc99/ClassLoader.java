package nl.ru.ai.jcc99;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassLoader
{
  public ClassLoader(String [] classPath)
  {
    for(String classPathEntry:classPath)
    {
      if(classPathEntry.toLowerCase().endsWith(".jar"))
      {
        /*
         * A jar file, we have to look inside
         */
        ZipInputStream zipInputStream;
        try
        {
          zipInputStream=new ZipInputStream(new FileInputStream(classPathEntry));
          ZipEntry entry;
          while((entry=zipInputStream.getNextEntry())!=null)
          {
            String name=entry.getName().toLowerCase();
            if(name.endsWith(".class"))
            {
              ByteArrayOutputStream byteStream=new ByteArrayOutputStream();
              copy(zipInputStream,byteStream);
              ByteBuffer buffer=ByteBuffer.wrap(byteStream.toByteArray());
              ClassFile classFile=new ClassFile(buffer);
              System.out.println(classFile.getName());
            }
          }
        }
        catch(FileNotFoundException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        catch(IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        
        
      } else
      {
        /*
         * Assume it is a folder look for the class on the file system
         */
        
      }
    }
  }
  
  private void copy(InputStream input, OutputStream output) throws IOException
  {
    byte[] buf=new byte[1024];
    while(true)
    {
      int length=input.read(buf);
      if(length<0)
        break;
      output.write(buf,0,length);
    }
  }

}
