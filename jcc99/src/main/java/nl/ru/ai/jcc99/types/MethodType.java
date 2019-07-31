package nl.ru.ai.jcc99.types;

import java.util.List;

import nl.ru.ai.jcc99.Coder;

public class MethodType implements Type
{
  private List<Type> arguments;
  private Type resultType;

  public MethodType(List<Type> arguments, Type resultType)
  {
    this.arguments=arguments;
    this.resultType=resultType;
  }
  
  public String toString()
  {
    StringBuffer buffer=new StringBuffer();
    buffer.append("Method(");
    for(int i=0;i<arguments.size();i++)
    {
      buffer.append(arguments.get(i));
      if(i!=arguments.size()-1)
        buffer.append(',');
    }
    buffer.append(')');
    buffer.append(resultType);
    return new String(buffer);
  }
  
  public int unitSize()
  {
    return 1;
  }

  public int parameterUnitSize()
  {
    int size=0;
    for(int i=0;i<arguments.size();i++)
      size+=arguments.get(i).unitSize();
    return size;
  }
  
  public int getNumberOfParameters()
  {
    return arguments.size();
  }

  public Type getResultType()
  {
    return resultType;
  }

  public void codeDebug(Coder coder)
  {
    for(int i=0;i<arguments.size();i++)
      coder.codeWord(arguments.get(i).codeDebugId());
  }

  public int codeDebugId()
  {
    return METHOD_ID;
  }
}
