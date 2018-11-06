package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import nl.ru.ai.jcc99.ClassLoader;
import nl.ru.ai.jcc99.Coder;
import nl.ru.ai.jcc99.Method;
import nl.ru.ai.jcc99.TypeSuffix;
import nl.ru.ai.jcc99.constants.Constant;
/**
 * https://en.wikipedia.org/wiki/Java_bytecode_instruction_listings
 * @author sparky
 *
 */
public abstract class Instruction
{
  protected int position;
  protected Constant[] constants;

  public Instruction(int position, Constant[] constants)
  {
    this.position=position;
    this.constants=constants;
  }
  
  public int getPosition()
  {
    return position;
  }

  public static List<Instruction> create(Constant[] constants, ByteBuffer buffer)
  {
    List<Instruction> instructions=new ArrayList<Instruction>();
    while(buffer.hasRemaining())
    {
      int position=buffer.position();
      int b=buffer.get()&0xff;
      Instruction instruction;
      switch(b)
      {
        case 0x00:
          instruction=new NopInstruction(position, constants);
          break;
        case 0x01:
          instruction=new AconstNullInstruction(position, constants);
          break;
        case 0x02:
        case 0x03:
        case 0x04:
        case 0x05:
        case 0x06:
        case 0x07:
        case 0x08:
          instruction=new IconstInstruction(position, constants,b-0x03);
          break;
        case 0x09:
        case 0x0a:
          instruction=new LconstInstruction(position, constants,(long)(b-0x09));
          break;
        case 0x0b:
        case 0x0c:
        case 0x0d:
          instruction=new FconstInstruction(position, constants,(float)(b-0x0b));
          break;
        case 0x0e:
        case 0x0f:
          instruction=new DconstInstruction(position, constants,(double)(b-0x0e));
          break;
        case 0x10:
          instruction=new BipushInstruction(position, constants,buffer.get());
          break;
        case 0x11:
          instruction=new SipushInstruction(position, constants,buffer.getShort());
          break;
        case 0x12:
          instruction=new LdcInstruction(position, constants,buffer.get()&0xff);
          break;
        case 0x13:
          instruction=new LdcWInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0x14:
          instruction=new Ldc2WInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0x15:
          instruction=new LoadInstruction(position, constants,TypeSuffix.INT,buffer.get()&0xff);
          break;
        case 0x16:
          instruction=new LoadInstruction(position, constants,TypeSuffix.LONG,buffer.get()&0xff);
          break;
        case 0x17:
          instruction=new LoadInstruction(position, constants,TypeSuffix.FLOAT,buffer.get()&0xff);
          break;
        case 0x18:
          instruction=new LoadInstruction(position, constants,TypeSuffix.DOUBLE,buffer.get()&0xff);
          break;
        case 0x19:
          instruction=new LoadInstruction(position, constants,TypeSuffix.REF,buffer.get()&0xff);
          break;
        case 0x1a:
        case 0x1b:
        case 0x1c:
        case 0x1d:
          instruction=new LoadInstruction(position, constants,TypeSuffix.INT,b-0x1a);
          break;
        case 0x1e:
        case 0x1f:
        case 0x20:
        case 0x21:
          instruction=new LoadInstruction(position, constants,TypeSuffix.LONG,b-0x1e);
          break;
        case 0x22:
        case 0x23:
        case 0x24:
        case 0x25:
          instruction=new LoadInstruction(position, constants,TypeSuffix.FLOAT,b-0x24);
          break;
        case 0x26:
        case 0x27:
        case 0x28:
        case 0x29:
          instruction=new LoadInstruction(position, constants,TypeSuffix.DOUBLE,b-0x26);
          break;
        case 0x2a:
        case 0x2b:
        case 0x2c:
        case 0x2d:
          instruction=new LoadInstruction(position, constants,TypeSuffix.REF,b-0x2a);
          break;
        case 0x2e:
          instruction=new AloadInstruction(position, constants,TypeSuffix.INT);
          break;
        case 0x2f:
          instruction=new AloadInstruction(position, constants,TypeSuffix.LONG);
          break;
        case 0x30:
          instruction=new AloadInstruction(position, constants,TypeSuffix.FLOAT);
          break;
        case 0x31:
          instruction=new AloadInstruction(position, constants,TypeSuffix.DOUBLE);
          break;
        case 0x32:
          instruction=new AloadInstruction(position, constants,TypeSuffix.REF);
          break;
        case 0x33:
          instruction=new AloadInstruction(position, constants,TypeSuffix.BOOLEAN);
          break;
        case 0x34:
          instruction=new AloadInstruction(position, constants,TypeSuffix.CHAR);
          break;
        case 0x35:
          instruction=new AloadInstruction(position, constants,TypeSuffix.SHORT);
          break;
        case 0x36:
          instruction=new StoreInstruction(position, constants,TypeSuffix.INT,buffer.get()&0xff);
          break;
        case 0x37:
          instruction=new StoreInstruction(position, constants,TypeSuffix.LONG,buffer.get()&0xff);
          break;
        case 0x38:
          instruction=new StoreInstruction(position, constants,TypeSuffix.FLOAT,buffer.get()&0xff);
          break;
        case 0x39:
          instruction=new StoreInstruction(position, constants,TypeSuffix.DOUBLE,buffer.get()&0xff);
          break;
        case 0x3a:
          instruction=new StoreInstruction(position, constants,TypeSuffix.REF,buffer.get()&0xff);
          break;
        case 0x3b:
        case 0x3c:
        case 0x3d:
        case 0x3e:
          instruction=new StoreInstruction(position, constants,TypeSuffix.INT,b-0x3b);
          break;
        case 0x3f:
        case 0x40:
        case 0x41:
        case 0x42:
          instruction=new StoreInstruction(position, constants,TypeSuffix.LONG,b-0x3f);
          break;
        case 0x43:
        case 0x44:
        case 0x45:
        case 0x46:
          instruction=new StoreInstruction(position, constants,TypeSuffix.FLOAT,b-0x43);
          break;
        case 0x47:
        case 0x48:
        case 0x49:
        case 0x4a:
          instruction=new StoreInstruction(position, constants,TypeSuffix.DOUBLE,b-0x47);
          break;
        case 0x4b:
        case 0x4c:
        case 0x4d:
        case 0x4e:
          instruction=new StoreInstruction(position, constants,TypeSuffix.REF,b-0x4b);
          break;
        case 0x4f:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.INT);
          break;
        case 0x50:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.LONG);
          break;
        case 0x51:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.FLOAT);
          break;
        case 0x52:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.DOUBLE);
          break;
        case 0x53:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.REF);
          break;
        case 0x54:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.BOOLEAN);
          break;
        case 0x55:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.CHAR);
          break;
        case 0x56:
          instruction=new AstoreInstruction(position, constants,TypeSuffix.SHORT);
          break;
        case 0x57:
          instruction=new PopInstruction(position, constants);
          break;
        case 0x58:
          instruction=new Pop2Instruction(position, constants);
          break;
        case 0x59:
          instruction=new DupInstruction(position, constants);
          break;
        case 0x5a:
          instruction=new DupX1Instruction(position, constants);
          break;
        case 0x5b:
          instruction=new DupX2Instruction(position, constants);
          break;
        case 0x5c:
          instruction=new Dup2Instruction(position, constants);
          break;
        case 0x5d:
          instruction=new Dup2X1Instruction(position, constants);
          break;
        case 0x5e:
          instruction=new Dup2X2Instruction(position, constants);
          break;
        case 0x5f:
          instruction=new SwapInstruction(position, constants);
          break;
        case 0x60:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.ADD);
          break;
        case 0x61:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG,Operator.ADD);
          break;
        case 0x62:
          instruction=new DyadInstruction(position, constants,TypeSuffix.FLOAT,Operator.ADD);
          break;
        case 0x63:
          instruction=new DyadInstruction(position, constants,TypeSuffix.DOUBLE, Operator.ADD);
          break;
        case 0x64:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.SUB);
          break;
        case 0x65:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG,Operator.SUB);
          break;
        case 0x66:
          instruction=new DyadInstruction(position, constants,TypeSuffix.FLOAT, Operator.SUB);
          break;
        case 0x67:
          instruction=new DyadInstruction(position, constants,TypeSuffix.DOUBLE, Operator.SUB);
          break;
        case 0x68:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.MUL);
          break;
        case 0x69:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG,Operator.MUL);
          break;
        case 0x6a:
          instruction=new DyadInstruction(position, constants,TypeSuffix.FLOAT,Operator.MUL);
          break;
        case 0x6b:
          instruction=new DyadInstruction(position, constants,TypeSuffix.DOUBLE, Operator.MUL);
          break;
        case 0x6c:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.DIV);
          break;
        case 0x6d:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG,Operator.DIV);
          break;
        case 0x6e:
          instruction=new DyadInstruction(position, constants,TypeSuffix.FLOAT,Operator.DIV);
          break;
        case 0x6f:
          instruction=new DyadInstruction(position, constants,TypeSuffix.DOUBLE, Operator.DIV);
          break;
        case 0x70:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.REM);
          break;
        case 0x71:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG,Operator.REM);
          break;
        case 0x72:
          instruction=new DyadInstruction(position, constants,TypeSuffix.FLOAT,Operator.REM);
          break;
        case 0x73:
          instruction=new DyadInstruction(position, constants,TypeSuffix.DOUBLE,Operator.REM);
          break;
        case 0x74:
          instruction=new InegInstruction(position, constants);
          break;
        case 0x75:
          instruction=new LnegInstruction(position, constants);
          break;
        case 0x76:
          instruction=new FnegInstruction(position, constants);
          break;
        case 0x77:
          instruction=new DnegInstruction(position, constants);
          break;
        case 0x78:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.SHL);
          break;
        case 0x79:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG,Operator.SHL);
          break;
        case 0x7a:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.SHR);
          break;
        case 0x7b:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG, Operator.SHR);
          break;
        case 0x7c:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.USHR);
          break;
        case 0x7d:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG, Operator.USHR);
          break;
        case 0x7e:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.AND);
          break;
        case 0x7f:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG, Operator.AND);
          break;
        case 0x80:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.OR);
          break;
        case 0x81:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG, Operator.OR);
          break;
        case 0x82:
          instruction=new DyadInstruction(position, constants,TypeSuffix.INT, Operator.XOR);
          break;
        case 0x83:
          instruction=new DyadInstruction(position, constants,TypeSuffix.LONG, Operator.XOR);
          break;
        case 0x84:
          instruction=new IincInstruction(position, constants,buffer.get()&0xff,buffer.get());
          break;
        case 0x85:
          instruction=new ConvertInstruction(position, constants,Conversion.I2L);
          break;
        case 0x86:
          instruction=new ConvertInstruction(position, constants,Conversion.I2F);
          break;
        case 0x87:
          instruction=new ConvertInstruction(position, constants,Conversion.I2D);
          break;
        case 0x88:
          instruction=new ConvertInstruction(position, constants,Conversion.L2I);
          break;
        case 0x89:
          instruction=new ConvertInstruction(position, constants,Conversion.L2F);
          break;
        case 0x8a:
          instruction=new ConvertInstruction(position, constants,Conversion.L2D);
          break;
        case 0x8b:
          instruction=new ConvertInstruction(position, constants,Conversion.F2I);
          break;
        case 0x8c:
          instruction=new ConvertInstruction(position, constants,Conversion.F2L);
          break;
        case 0x8d:
          instruction=new ConvertInstruction(position, constants,Conversion.F2D);
          break;
        case 0x8e:
          instruction=new ConvertInstruction(position, constants,Conversion.D2I);
          break;
        case 0x8f:
          instruction=new ConvertInstruction(position, constants,Conversion.D2L);
          break;
        case 0x90:
          instruction=new ConvertInstruction(position, constants,Conversion.D2F);
          break;
        case 0x91:
          instruction=new ConvertInstruction(position, constants,Conversion.I2B);
          break;
        case 0x92:
          instruction=new ConvertInstruction(position, constants,Conversion.I2C);
          break;
        case 0x93:
          instruction=new ConvertInstruction(position, constants,Conversion.I2S);
          break;
        case 0x94:
          instruction=new LcmpInstruction(position, constants);
          break;
        case 0x95:
          instruction=new FcmplInstruction(position, constants);
          break;
        case 0x96:
          instruction=new FcmpgInstruction(position, constants);
          break;
        case 0x97:
          instruction=new DcmplInstruction(position, constants);
          break;
        case 0x98:
          instruction=new DcmpgInstruction(position, constants);
          break;
        case 0x99:
          instruction=new IfInstruction(position, constants,Condition.EQ,buffer.getShort());
          break;
        case 0x9a:
          instruction=new IfInstruction(position, constants,Condition.NE,buffer.getShort());
          break;
        case 0x9b:
          instruction=new IfInstruction(position, constants,Condition.LT,buffer.getShort());
          break;
        case 0x9c:
          instruction=new IfInstruction(position, constants,Condition.GE,buffer.getShort());
          break;
        case 0x9d:
          instruction=new IfInstruction(position, constants,Condition.GT,buffer.getShort());
          break;
        case 0x9e:
          instruction=new IfInstruction(position, constants,Condition.LE,buffer.getShort());
          break;
        case 0x9f:
          instruction=new IfIcmpInstruction(position, constants,Condition.EQ,buffer.getShort());
          break;
        case 0xa0:
          instruction=new IfIcmpInstruction(position, constants,Condition.NE,buffer.getShort());
          break;
        case 0xa1:
          instruction=new IfIcmpInstruction(position, constants,Condition.LT,buffer.getShort());
          break;
        case 0xa2:
          instruction=new IfIcmpInstruction(position, constants,Condition.GE,buffer.getShort());
          break;
        case 0xa3:
          instruction=new IfIcmpInstruction(position, constants,Condition.GT,buffer.getShort());
          break;
        case 0xa4:
          instruction=new IfIcmpInstruction(position, constants,Condition.LE,buffer.getShort());
          break;
        case 0xa5:
          instruction=new IfAcmpInstruction(position, constants,Condition.EQ,buffer.getShort());
          break;
        case 0xa6:
          instruction=new IfAcmpInstruction(position, constants,Condition.NE,buffer.getShort());
          break;
        case 0xa7:
          instruction=new GotoInstruction(position, constants,buffer.getShort());
          break;
        case 0xa8:
          instruction=new JsrInstruction(position, constants,buffer.getShort());
          break;
        case 0xa9:
          instruction=new RetInstruction(position, constants,buffer.get()&0xff);
          break;
        case 0xaa:
          instruction=new TableswitchInstruction(position, constants,buffer);
          break;
        case 0xab:
          instruction=new Lookupswitch(position, constants,buffer);
          break;
        case 0xac:
          instruction=new IreturnInstruction(position, constants);
          break;
        case 0xad:
          instruction=new LreturnInstruction(position, constants);
          break;
        case 0xae:
          instruction=new FreturnInstruction(position, constants);
          break;
        case 0xaf:
          instruction=new DreturnInstruction(position, constants);
          break;
        case 0xb0:
          instruction=new AreturnInstruction(position, constants);
          break;
        case 0xb1:
          instruction=new ReturnInstruction(position, constants);
          break;
        case 0xb2:
          instruction=new GetstaticInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xb3:
          instruction=new PutstaticInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xb4:
          instruction=new GetfieldInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xb5:
          instruction=new PutfieldInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xb6:
          instruction=new InvokevirtualInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xb7:
          instruction=new InvokespecialInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xb8:
          instruction=new InvokestaticInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xb9:
          instruction=new InvokeinterfaceInstruction(position, constants,buffer.getShort()&0xffff,buffer.get()&0xff,buffer.get());
          break;
        case 0xba:
          instruction=new InvokedynamicInstruction(position, constants,buffer.getShort()&0xffff,buffer.get(),buffer.get());
          break;
        case 0xbb:
          instruction=new NewInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xbc:
          instruction=new NewarrayInstruction(position, constants,TypeSuffix.values()[buffer.get()]);
          break;
        case 0xbd:
          instruction=new AnewarrayInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xbe:
          instruction=new ArraylengthInstruction(position, constants);
          break;
        case 0xbf:
          instruction=new AthrowInstruction(position, constants);
          break;
        case 0xc0:
          instruction=new CheckcastInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xc1:
          instruction=new InstanceofInstruction(position, constants,buffer.getShort()&0xffff);
          break;
        case 0xc2:
          instruction=new MonitorEnterInstruction(position, constants);
          break;
        case 0xc3:
          instruction=new MonitorExitInstructions(position, constants);
          break;
        case 0xc4:
          int subCode=buffer.get();
          switch(subCode)
          {
            case 0x15:
              instruction=new LoadInstruction(position, constants,TypeSuffix.INT,buffer.getShort()&0xffff);
              break;
            case 0x16:
              instruction=new LoadInstruction(position, constants,TypeSuffix.LONG,buffer.getShort()&0xffff);
              break;
            case 0x17:
              instruction=new LoadInstruction(position, constants,TypeSuffix.FLOAT,buffer.getShort()&0xffff);
              break;
            case 0x18:
              instruction=new LoadInstruction(position, constants,TypeSuffix.DOUBLE,buffer.getShort()&0xffff);
              break;
            case 0x19:
              instruction=new LoadInstruction(position, constants,TypeSuffix.REF,buffer.getShort()&0xffff);
              break;
            case 0x36:
              instruction=new StoreInstruction(position, constants,TypeSuffix.INT,buffer.getShort()&0xffff);
              break;
            case 0x37:
              instruction=new StoreInstruction(position, constants,TypeSuffix.LONG,buffer.getShort()&0xffff);
              break;
            case 0x38:
              instruction=new StoreInstruction(position, constants,TypeSuffix.FLOAT,buffer.getShort()&0xffff);
              break;
            case 0x39:
              instruction=new StoreInstruction(position, constants,TypeSuffix.DOUBLE,buffer.getShort()&0xffff);
              break;
            case 0x3a:
              instruction=new StoreInstruction(position, constants,TypeSuffix.REF,buffer.getShort()&0xffff);
              break;
            case 0x84:
              instruction=new IincInstruction(position, constants,buffer.getShort()&0xffff,buffer.getShort());
              break;
            case 0xa9:
              instruction=new RetInstruction(position, constants,buffer.getShort()&0xffff);
              break;
            default:
              throw new RuntimeException(String.format("invalid wide sub opcode '%02x'",subCode));
          }
          break;
        case 0xc5:
          instruction=new Multianewarray(position, constants,buffer.getShort(),buffer.get()&0xff);
          break;
        case 0xc6:
          instruction=new IfInstruction(position, constants,Condition.NULL,buffer.getShort());
          break;
        case 0xc7:
          instruction=new IfInstruction(position, constants,Condition.NONNULL,buffer.getShort());
          break;
        case 0xc8:
          instruction=new GotoInstruction(position, constants,buffer.getInt());
          break;
        case 0xc9:
          instruction=new JsrInstruction(position, constants,buffer.getInt());
          break;
        case 0xca:
          instruction=new BreakpointInstruction(position, constants);
          break;
        case 0xfe:
        case 0xff:
          instruction=new ImdepInstruction(position, constants,b-0xfe);
          break;
        default:
          throw new RuntimeException(String.format("invalid opcode '%02x'",b));
      }
      instructions.add(instruction);
    }
    return instructions;
  }

  public abstract void analyze(ClassLoader classLoader, Method method);
  public abstract void code(ClassLoader classLoader, Method method, Coder coder);
}
