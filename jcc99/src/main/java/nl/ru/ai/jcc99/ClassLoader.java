package nl.ru.ai.jcc99;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import nl.ru.ai.jcc99.constants.OutlineConstant;

public class ClassLoader
{
  private Map<String, ClassFile> classByName;
  private Map<String, Method> staticMethodByName;
  private Map<String, Method> dynamicMethodByName;
  private Map<String, Field> staticFieldByName;
  private List<OutlineConstant> constantPool;
  private int nextLabel;
  private List<Method> init;
  private List<ClassFile> collected;
  private List<ClassFile> needed;

  public ClassLoader(String[] classPath)
  {
    classByName=new HashMap<String, ClassFile>();
    staticMethodByName=new HashMap<String, Method>();
    dynamicMethodByName=new HashMap<String, Method>();
    staticFieldByName=new HashMap<String, Field>();
    constantPool=new ArrayList<OutlineConstant>();
    collected=new ArrayList<ClassFile>();
    needed=new ArrayList<ClassFile>();
    init=new ArrayList<Method>();
    nextLabel=0;

    for(String classPathEntry : classPath)
    {
      File file=new File(classPathEntry);
      if(file.isFile()&&classPathEntry.toLowerCase().endsWith(".jar"))
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
                administrate(new ClassFile(this,buffer));
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
      for(File subFile : file.listFiles())
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
            administrate(new ClassFile(this,buffer));
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

  private void administrate(ClassFile classFile)
  {
    classByName.put(classFile.getName(),classFile);
    for(Method method : classFile.getMethods())
    {
      String methodName=method.getFullName();
      if(method.isStatic())
        staticMethodByName.put(methodName,method);
      else
        dynamicMethodByName.put(methodName,method);
    }
    for(Field field : classFile.getFields())
      if(field.isStatic())
      {
        String fieldName=field.getFullName();
        staticFieldByName.put(fieldName,field);
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

  public Method getStaticMethod(String name)
  {
    return staticMethodByName.get(name);
  }

  public Field getStaticField(String name)
  {
    return staticFieldByName.get(name);
  }

  public Method getDynamicMethod(String name)
  {
    return dynamicMethodByName.get(name);
  }

  /**
   * For debugging
   */
  public void dump()
  {
    System.out.println("Static methods marked for coding:");
    for(String methodName : staticMethodByName.keySet())
    {
      Method method=staticMethodByName.get(methodName);
      if(method.isAnalyzed())
        System.out.println(methodName+":"+method);
    }
    System.out.println("Dynamic methods marked for coding:");
    for(String methodName : dynamicMethodByName.keySet())
    {
      Method method=dynamicMethodByName.get(methodName);
      if(method.isAnalyzed())
        System.out.println(methodName+":"+method);
    }
  }

  public ClassFile getClassFile(String className)
  {
    return classByName.get(className);
  }

  public void code(Method mainMethod, Coder coder)
  {
    /*
     * Preamble
     */
    coder.codeComment("Code generated by jcc using the "+coder.getVersion());
    /*
     * Code entry
     */
    coder.codeEntry();
    /*
     * Code call to class initialization methods
     */
    for(int i=init.size()-1;i>=0;i--)
      coder.codeCall(init.get(i));
    /*
     * Prepare the c arguments for main method
     */
    coder.codePrepare();
    /*
     * Call main
     */
    coder.codeCall(mainMethod);
    /*
     * Code exit
     */
    coder.codeExit();
    /*
     * Code all methods
     */
    for(String methodName : staticMethodByName.keySet())
    {
      Method method=staticMethodByName.get(methodName);
      if(method.isAnalyzed()&&!method.isNative())
        method.code(this,coder);
    }
    for(String methodName : dynamicMethodByName.keySet())
    {
      Method method=dynamicMethodByName.get(methodName);
      if(method.isAnalyzed()&&!method.isNative())
        method.code(this,coder);
    }
    /*
     * Generate all static fields
     */
    for(String fieldName : staticFieldByName.keySet())
    {
      Field field=staticFieldByName.get(fieldName);
      field.code(this,coder);
    }
    /*
     * Generate constant pool in data segment
     */
    coder.codeData();
    for(OutlineConstant constant : constantPool)
      constant.code(coder);
    /*
     * Generate all class vectors
     */
    coder.codeAlignWord();
    for(ClassFile classFile : needed)
      classFile.codeVector(coder);
    /*
     * Generate class info structure and interfaces
     */
    for(ClassFile classFile : needed)
      classFile.codeInfo(coder);
    for(ClassFile classFile : needed)
      classFile.codeInterfaces(coder);
    /*
     * Generate all class names
     */
    for(ClassFile classFile : needed)
      classFile.codeName(coder);
    /*
     * Generate heap
     */
    coder.codeHeap();
  }

  public int addConstant(OutlineConstant constant)
  {
    int result=constantPool.size();
    constantPool.add(constant);
    return result;
  }

  public String getNextLabel()
  {
    return String.format("L%d",nextLabel++);
  }

  public void addInitialization(Method method)
  {
    init.add(method);
  }

  public void analyzeDynamicMethods()
  {
    do
    {
      List<ClassFile> toDo=collected;
      collected=new ArrayList<ClassFile>();
      /*
       * Analyze the Dynamic Methods until no new classes are added
       */
      for(ClassFile classFile:toDo)
      {
        classFile.analyze();
        classFile.analyzeDynamicMethods();
      }
    } while(collected.size()!=0);
  }

  public void collect(ClassFile classFile)
  {
    collected.add(classFile);
    needed.add(classFile);
  }
}
