package nl.ru.ai.jcc99;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassLoader
{
  private Map<String, ClassFile> classByName;

  public ClassLoader(String [] classPath)
  {
    classByName=new HashMap<String,ClassFile>();
    for(String classPathEntry:classPath)
    {
      File file=new File(classPathEntry);
      if(file.isFile() && classPathEntry.toLowerCase().endsWith(".jar"))
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
              try
              {
                ClassFile classFile=new ClassFile(buffer);
                classByName.put(classFile.getName(),classFile);
              }
              catch(ClassLoaderException e)
              {
                System.err.printf("Error loading class '%s' from '%s': %s\n",name,classPathEntry,e.getMessage());
                System.exit(1);
              }
            }
          }
        }
        catch(FileNotFoundException e)
        {
          System.err.printf("Error opening '%s': %s\n",classPathEntry,e.getMessage());
          System.exit(1);
        }
        catch(IOException e)
        {
          System.err.printf("Error reading '%s': %s\n",classPathEntry,e.getMessage());
          System.exit(1);
        }
      } else if(file.isDirectory())
      {
        /*
         * Directory: look for classes on the file system
         */
        addClassFile(file);
      } else
      {
        System.err.printf("Error: illegal classpath entry '%s', neither a folder nor a jar file\n",classPathEntry);
        System.exit(1);
      }
    }
  }
  
  private void addClassFile(File file)
  {
    if(file.isDirectory())
      for(File subFile:file.listFiles())
        addClassFile(subFile);
    else
    {
      String name=file.getAbsolutePath();
      if(name.toLowerCase().endsWith(".class"))
      {
        try
        {
          FileInputStream inputStream=new FileInputStream(file);
          ByteArrayOutputStream byteStream=new ByteArrayOutputStream();
          copy(inputStream,byteStream);
          ByteBuffer buffer=ByteBuffer.wrap(byteStream.toByteArray());
          try
          {
            ClassFile classFile=new ClassFile(buffer);
            classByName.put(classFile.getName(),classFile);
          }
          catch(ClassLoaderException e)
          {
            System.err.printf("Error loading class '%s': %s\n",name,e.getMessage());
            System.exit(1);
          }
        }
        catch(FileNotFoundException e)
        {
          System.err.printf("Error opening '%s': %s\n",name,e.getMessage());
          System.exit(1);
        }
        catch(IOException e)
        {
          System.err.printf("Error reading '%s': %s\n",name,e.getMessage());
          System.exit(1);
        }
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

  public ClassFile getClass(String name)
  {
    return classByName.get(name);
  }

}
