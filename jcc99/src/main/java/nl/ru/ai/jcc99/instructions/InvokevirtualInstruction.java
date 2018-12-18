package nl.ru.ai.jcc99.instructions;

import nl.ru.ai.jcc99.ClassFile;
import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.Util;
import nl.ru.ai.jcc99.constants.Constant;
import nl.ru.ai.jcc99.constants.MethodrefConstant;

public class InvokevirtualInstruction extends Instruction
{
  private int methodIndex;
  private Method subMethod;

  public InvokevirtualInstruction(int position, Constant[] constants, int methodIndex)
  {
    super(position,constants);
    this.methodIndex=methodIndex;
  }
  
  public void analyze(ClassLoader classLoader, Method method)
  {
    String name = constants[methodIndex].toShortString();
    MethodrefConstant methodrefConstant=(MethodrefConstant)constants[methodIndex];
    String className=constants[methodrefConstant.getClassIndex()].toShortString();
    String nameAndTypeString=constants[methodrefConstant.getNameAndTypeIndex()].toShortString();
	while((subMethod=classLoader.getDynamicMethod(className+"."+nameAndTypeString))==null)
	{
	  ClassFile classFile=classLoader.getClassFile(className);
	  className=classFile.getSuperClass();
	  if(className==null)
	  {
	    Util.error("Cannot analyze '%s' for invokevirtual",name);
	    return;
	  }
	}
    // Analyzing the method itself is done later (all non static methods of needed classes should be coded)
  }
  
  public String toString()
  {
    return String.format("invokevirtual %d (%s)",methodIndex,constants[methodIndex].toShortString());
  }

  public void code(ClassLoader classLoader, Method method, Coder coder)
  {
    coder.codeDynamicCall(subMethod);
  }
}
