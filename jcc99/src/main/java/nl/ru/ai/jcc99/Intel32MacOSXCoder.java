package nl.ru.ai.jcc99;

import java.io.PrintWriter;

import javax.management.RuntimeErrorException;
import javax.print.DocFlavor.CHAR_ARRAY;

import nl.ru.ai.jcc99.constants.OutlineConstant;
import nl.ru.ai.jcc99.instructions.Condition;

/*
 * FIXME: interesting, optimize array access
 * movl    -8(%ebp, %edx, 4), %eax  # Full example: load *(ebp + (edx * 4) - 8) into eax
 * https://en.wikibooks.org/wiki/X86_Assembly/GAS_Syntax
 */
/*
 * Stack frame usuage 
 * Note stack is predecrement, so sp points to top (used) element growing upward 
 * (from high address to low addresses).
 * Imagine we have 3 parameters: a, b and c and 2 locals, d and e
 * The caller will push a b and c on the stack (in that order) and will use call
 * to reach us. This means that return address is also on the stack
 * Upon entry we follow the link procedure: push fp on stack, save the resulting 
 * sp as fp and allocate space for the local variables
 * The stack layout is like this:
 *
 *          +-------------+
 *  sp ---> | local e     |
 *          +-------------+
 *          | local d     |
 *          +-------------+
 *  fp ---> | old fp      |
 *          +-------------+
 *          | return      |
 *          +-------------+
 *          | parameter c |
 *          +-------------+
 *          | parameter b |
 *          +-------------+
 *          | parameter a |
 *          +-------------+
 *  In bytecode the parameters are numbered. a has number 0, b number 1 and c number 2.
 *  Let i be their number, n be the number of parameters (arity), and w the wordsize,
 *  The paramters are reachable through fp with the offset (n+1-i)*w
 *  The locals are numbered as well, d has number 3 and e has number 4
 *  The locals are reachable through fp with the offset -(i-n+1)*w
 *  
 */
public class Intel32MacOSXCoder implements Coder
{
  private PrintWriter writer;
  private LabelDisambiguator disambiguator;

  public Intel32MacOSXCoder(PrintWriter writer, LabelDisambiguator disambiguator)
  {
    this.writer=writer;
    this.disambiguator=disambiguator;
  }

  public short getWordSize()
  {
    return 4;
  }

  private int bitShift(TypeSuffix type)
  {
    switch(type)
    {
      case BOOLEAN:
      case BYTE:
        return 0;
      case CHAR:
      case SHORT:
        return 1;
      case INT:
      case FLOAT:
        return 2;
      default:
        throw new RuntimeException("Illegal type");
    }
  }

  public void codeEntry()
  {
    writer.printf("\t.globl\t _main\n");
    writer.printf("_main:\n");
    writer.printf("\tlea\theapstart,%%eax\n");
    writer.printf("\tmovl\t%%eax,heapptr\n");
  }

  public void codeHeap()
  {
    writer.printf("\t.data\n");
    writer.printf("\t.align\t4\n");
    writer.printf("heapptr:\t.long\t0\n");
    writer.printf("heapstart:\n");
  }

  public void codeLabel(Method method)
  {
    writer.printf("%s:\n",disambiguator.name(method));
  }

  public void codeLabel(OutlineConstant constant)
  {
    writer.printf("%s:\n",constant.getLabel());
  }

  public String getVersion()
  {
    return "MacOSX Intel 32 bit coder";
  }

  public void close()
  {
    writer.close();
  }

  private int offset(int parameterUnits, int local)
  {
    if(local<parameterUnits)
      return (parameterUnits+1-local)*getWordSize(); // parameter
    return -(local-parameterUnits+1)*getWordSize(); // local
  }

  public void codeComment(String comment)
  {
    StringBuffer buffer=new StringBuffer();
    buffer.append("\t# ");
    for(int i=0;i<comment.length();i++)
    {
      char c=comment.charAt(i);
      switch(c)
      {
        case '\n':
          buffer.append("\\n");
          break;
        case '\r':
          buffer.append("\\r");
          break;
        case '\t':
          buffer.append("\\t");
          break;
        default:
          buffer.append(c);
          break;
      }
    }
    buffer.append("\n");
    writer.write(new String(buffer));
  }

  public void codeLink(int number)
  {
    writer.printf("\tpushl\t%%ebp\n");
    writer.printf("\tmovl\t%%esp,%%ebp\n");
    writer.printf("\tsubl\t$%d,%%esp\n",number*getWordSize());
  }

  public void codeLoad(int parameterUnits, int local)
  {
    writer.printf("\tpushl\t%d(%%ebp)\n",offset(parameterUnits,local));
  }

  public void codeIntInc(int parameterUnits, int local, int value)
  {
    writer.printf("\taddl\t$%d,%d(%%ebp)\n",value,offset(parameterUnits,local));
  }

  public void codeDload(int parameterUnits, int local)
  {
    throw new RuntimeException("notyet");
  }

  public void codeAddInt()
  {
    writer.printf("\tpopl\t%%eax\n");
    writer.printf("\taddl\t%%eax,(%%esp)\n");
  }

  public void codeAndInt()
  {
    writer.printf("\tpopl\t%%eax\n");
    writer.printf("\tandl\t%%eax,(%%esp)\n");
  }

  public void codeStore(int parameterUnits, int local)
  {
    writer.printf("\tpopl\t%d(%%ebp)\n",offset(parameterUnits,local));
  }

  public void codeDStore(int parameterUnits, int local)
  {
    throw new RuntimeException("notyet");
  }

  public void codeReturnSingle(int parameterUnits)
  {
    writer.printf("\tpopl\t%%eax\n"); // get value to return
    writer.printf("\tmovl\t%%ebp,%%esp\n"); // unlink stack frame
    writer.printf("\tpopl\t%%ebp\n");
    writer.printf("\tpopl\t%%ecx\n"); // save return address
    writer.printf("\taddl\t$%d,%%esp\n",parameterUnits*getWordSize()); // remove parameters
    writer.printf("\tpushl\t%%eax\n"); // push return value
    writer.printf("\tjmpl\t*%%ecx\n"); // return
  }

  public void codeReturn(int parameterUnits)
  {
    writer.printf("\tmovl\t%%ebp,%%esp\n"); // unlink stack frame
    writer.printf("\tpopl\t%%ebp\n");
    writer.printf("\tpopl\t%%ecx\n"); // save return address
    writer.printf("\taddl\t$%d,%%esp\n",parameterUnits*getWordSize()); // remove parameters
    writer.printf("\tjmpl\t*%%ecx\n"); // return
  }

  public void codePushInt(int value)
  {
    writer.printf("\tpushl\t$%d\n",value);
  }

  public void codeCall(Method method)
  {
    writer.printf("\tcall\t%s\n",disambiguator.name(method));
  }
  
  public void codePushAddress(OutlineConstant constant)
  {
    writer.printf("\tleal\t%s,%%eax\n",constant.getLabel());
    writer.printf("\tpush\t%%eax\n");
  }

  public void codeData()
  {
    writer.printf("\t.data\n");
  }

  public void codeAsciz(String string)
  {
    StringBuffer buffer=new StringBuffer();
    buffer.append("\t.asciz \"");
    for(int i=0;i<string.length();i++)
    {
      char c=string.charAt(i);
      switch(c)
      {
        case '\n':
          buffer.append("\\n");
          break;
        case '\r':
          buffer.append("\\r");
          break;
        case '\t':
          buffer.append("\\t");
          break;
        default:
          buffer.append(c);
          break;
      }
    }
    buffer.append("\"\n");
    writer.write(new String(buffer));
  }

  /**
   * Allocate an array on the heap
   * Arrays are stored in the heap starting with a word that indicates the size
   * followed by the data (rounded up to full words)
   * @param elementType type of element
   */
  public void codeAllocateArray(TypeSuffix elementType)
  {
    writer.printf("\tpopl\t%%eax\n"); // the size of the array
    writer.printf("\tmovl\theapptr,%%ebx\n"); // get heapptr in ebx
    writer.printf("\tpushl\t%%ebx\n"); // the address of the array as the result
    writer.printf("\tmovl\t%%eax,(%%ebx)\n"); // store length
    writer.printf("\tshll\t$%d,%%eax\n",bitShift(elementType)); // multiply number of elements by type length
    writer.printf("\taddl\t$%d,%%eax\n",getWordSize()+getWordSize()-1); // one wordsize for the length, wordsize-1 for rounding
    writer.printf("\tandl\t$0xfffffffc,%%eax\n"); // round it
    writer.printf("\taddl\t%%eax,heapptr\n"); // adjust heap pointer
  }

  public void codePushByte(int value)
  {
    /*
     * Sign extend the byte into an int and push
     */
    value<<=24;
    value>>=24;
    writer.printf("\tpushl\t$%d\n",value);
  }

  public void codeArrayStore(TypeSuffix elementType)
  {
    writer.printf("\tpopl\t%%eax\n"); // value to store
    writer.printf("\tpopl\t%%ebx\n"); // index position
    writer.printf("\tshll\t$%d,%%ebx\n",bitShift(elementType)); // multiple by element size
    writer.printf("\tpopl\t%%ecx\n"); // address of array
    writer.printf("\tadd\t%%ebx,%%ecx\n"); // add it together
    switch(elementType)
    {
      case BYTE:
      case BOOLEAN:
        writer.printf("\tmovb\t%%al,%d(%%ecx)\n",getWordSize()); // store value
        break;
      case CHAR:
        writer.printf("\tmovw\t%%ax,%d(%%ecx)\n",getWordSize()); // store value
        break;
      case INT:
      case FLOAT:
        writer.printf("\tmovl\t%%eax,%d(%%ecx)\n",getWordSize()); // store value
        break;
      default:
        throw new RuntimeException("invalid type");
    }
  }

  public void codeArrayLoad(TypeSuffix elementType)
  {
    writer.printf("\tpopl\t%%ebx\n"); // index position
    writer.printf("\tshll\t$%d,%%ebx\n",bitShift(elementType)); // multiple by element size
    writer.printf("\tpopl\t%%ecx\n"); // address of array
    writer.printf("\tadd\t%%ebx,%%ecx\n"); // add it together
    switch(elementType)
    {
      case BYTE:
      case BOOLEAN:
        writer.printf("\tclrl\t%%eax\n"); //FIXME: needed? sign extend?
        writer.printf("\tmovb\t%d(%%ecx),%%al\n",getWordSize()); // store value
        break;
      case CHAR:
        writer.printf("\tclrl\t%%eax\n"); //FIXME: needed? sign extend?
        writer.printf("\tmovw\t%d(%%ecx),%%ax\n",getWordSize()); // store value
        break;
      case INT:
      case FLOAT:
        writer.printf("\tmovl\t%d(%%ecx),%%eax\n",getWordSize()); // store value
        break;
      default:
        throw new RuntimeException("invalid type");
    }
    writer.printf("\tpushl\t%%eax\n");
  }

  public void codeAllocateObject(int size)
  {
    writer.printf("\tpushl\theapptr\n");
    writer.printf("\taddl\t$%d,heapptr\n",size*getWordSize());
  }

  public void codeDup()
  {
    writer.printf("\tpushl\t(%%esp)\n");

  }

  public void codePutField(int offset)
  {
    writer.printf("\tpopl\t%%eax\n"); //value to store
    writer.printf("\tpopl\t%%ebx\n"); //this
    writer.printf("\tmovl\t%%eax,%d(%%ebx)\n",offset*getWordSize());
  }

  public void codeArrayLength()
  {
    writer.printf("\tpopl\t%%eax\n"); // address of array
    writer.printf("\tpushl\t(%%eax)\n"); // push length
  }

  public void codeGetField(int offset)
  {
    writer.printf("\tpopl\t%%eax\n"); //this
    writer.printf("\tpushl\t%d(%%eax)\n",offset*getWordSize());
  }

  public void codeJump(String label)
  {
    writer.printf("\tjmp\t%s\n",label);
  }

  public void codeJump(Method method)
  {
    writer.printf("\tjmp\t%s\n",disambiguator.name(method));
  }
  
  public void codeLabel(String label)
  {
    writer.printf("%s:\n",label);
  }

  public void codeIntToByte()
  {
    writer.printf("\tmovsbl\t(%%esp),%%eax\n");
    writer.printf("\tmovl\t%%eax,(%%esp)\n");
  }

  public void codeIntCompare(Condition condition, String label)
  {
    writer.printf("\tpopl\t%%eax\n");
    writer.printf("\tpopl\t%%ebx\n");
    writer.printf("\tcmpl\t%%eax,%%ebx\n");
    /*
     * Switch to SIGNED jumps only
     */
    switch(condition)
    {
      case LT:
        writer.printf("\tjl\t%s\n",label);
        break;
      default:
        throw new RuntimeException("notyet");

    }
  }

  public void codePush(Field field)
  {
    writer.printf("\tpushl\t%s\n",disambiguator.name(field));
  }

  public void codePop(Field field)
  {
    writer.printf("\tpopl\t%s\n",disambiguator.name(field));
  }

  public void codeBss(Field field)
  {
    writer.printf("%s:\t.long\t0\n",disambiguator.name(field));
  }

}
