package nl.ru.ai.jcc99;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import nl.ru.ai.jcc99.types.ArrayType;
import nl.ru.ai.jcc99.types.BooleanType;
import nl.ru.ai.jcc99.types.CharacterType;
import nl.ru.ai.jcc99.types.ClassType;
import nl.ru.ai.jcc99.types.DoubleType;
import nl.ru.ai.jcc99.types.FloatType;
import nl.ru.ai.jcc99.types.IntegerType;
import nl.ru.ai.jcc99.types.LongType;
import nl.ru.ai.jcc99.types.MethodType;
import nl.ru.ai.jcc99.types.ShortType;
import nl.ru.ai.jcc99.types.Type;
import nl.ru.ai.jcc99.types.VoidType;

public class Util
{
  private static int numberOfErrors=0;

  public static void error(String format, Object... args)
  {
    String message=String.format(format,args);
    System.err.println(message);
    numberOfErrors++;
  }

  public static boolean foundErrors()
  {
    return numberOfErrors!=0;
  }

  public static Type convert(String descriptor)
  {
    CharBuffer buffer=CharBuffer.wrap(descriptor);
    Type type=convert(buffer);
    if(buffer.hasRemaining())
      throw new RuntimeException("Invalid descriptor");
    return type;
  }

  private static Type convert(CharBuffer descriptor)
  {
    char c=descriptor.get();
    switch(c)
    {
      case 'B':
        return new BooleanType();
      case 'C':
        return new CharacterType();
      case 'D':
        return new DoubleType();
      case 'F':
        return new FloatType();
      case 'I':
        return new IntegerType();
      case 'J':
        return new LongType();
      case 'S':
        return new ShortType();
      case 'Z':
        return new BooleanType();
      case 'V':
        return new VoidType();
      case 'L':
        StringBuffer typeName=new StringBuffer();
        while((c=descriptor.get())!=';')
          typeName.append(c);
        return new ClassType(new String(typeName));
      case '[':
        Type elementType=convert(descriptor);
        return new ArrayType(elementType);
      case '(':
        List<Type> arguments=new ArrayList<Type>();
        while((c=descriptor.get())!=')')
        {
          descriptor.position(descriptor.position()-1);
          arguments.add(convert(descriptor));
        }
        Type resultType=convert(descriptor);
        return new MethodType(arguments,resultType);
      default:
        throw new RuntimeException("Cannot parse descriptor, char='"+c+"'");
    }
  }
}
