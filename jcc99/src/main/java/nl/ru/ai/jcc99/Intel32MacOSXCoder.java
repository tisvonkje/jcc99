package nl.ru.ai.jcc99;

import java.io.PrintWriter;

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

  public void codeEntry(Method method)
  { 
    writer.printf("\t.globl\t _main\n");
    writer.printf("_main:\tjmp\t%s\n",disambiguator.name(method));
  }

  public void codeLabel(Method method)
  {
    writer.printf("%s:\n",disambiguator.name(method));
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
    writer.printf("# %s\n",comment);
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

  public void codeDload(int parameterUnits, int local)
  {
    throw new RuntimeException("notyet");
  }

  public void codeAddInt()
  {
    writer.printf("\tpopl\t%%eax\n");
    writer.printf("\taddl\t%%eax,(%%esp)\n");
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
    writer.printf("\tpopl\t%%eax\n");
    writer.printf("\tmovl\t(%%ebp),%%esp\n");
    writer.printf("\tpopl\t%%ebp\n");
    writer.printf("\tpopl\t%%ecx\n");
    writer.printf("\tpushl\t%%eax\n");
    writer.printf("\tjmpl\t*%%ecx\n");
  }
}
