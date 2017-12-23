package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import nl.ru.ai.jcc99.Type;
import nl.ru.ai.jcc99.constants.Constant;
/**
 * https://en.wikipedia.org/wiki/Java_bytecode_instruction_listings
 * @author sparky
 *
 */
public abstract class Instruction
{
  protected Constant[] constants;

  public Instruction(Constant[] constants)
  {
    this.constants=constants;
  }

  public static List<Instruction> create(Constant[] constants, ByteBuffer buffer)
  {
    List<Instruction> instructions=new ArrayList<Instruction>();
    while(buffer.hasRemaining())
    {
      int b=buffer.get()&0xff;
      Instruction instruction;
      switch(b)
      {
        case 0x00:
          instruction=new NopInstruction(constants);
          break;
        case 0x01:
          instruction=new AconstNullInstruction(constants);
          break;
        case 0x02:
        case 0x03:
        case 0x04:
        case 0x05:
        case 0x06:
        case 0x07:
        case 0x08:
          instruction=new IconstInstruction(constants,b-0x03);
          break;
        case 0x09:
        case 0x0a:
          instruction=new LconstInstruction(constants,(long)(b-0x09));
          break;
        case 0x0b:
        case 0x0c:
        case 0x0d:
          instruction=new FconstInstruction(constants,(float)(b-0x0b));
          break;
        case 0x0e:
        case 0x0f:
          instruction=new DconstInstruction(constants,(double)(b-0x0e));
          break;
        case 0x10:
          instruction=new BipushInstruction(constants,buffer.get());
          break;
        case 0x11:
          instruction=new SipushInstruction(constants,buffer.getShort());
          break;
        case 0x12:
          instruction=new LdcInstruction(constants,buffer.get()&0xff);
          break;
        case 0x13:
          instruction=new LdcWInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0x14:
          instruction=new Ldc2WInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0x15:
          instruction=new LoadInstruction(constants,Type.INT,buffer.get()&0xff);
          break;
        case 0x16:
          instruction=new LoadInstruction(constants,Type.LONG,buffer.get()&0xff);
          break;
        case 0x17:
          instruction=new LoadInstruction(constants,Type.FLOAT,buffer.get()&0xff);
          break;
        case 0x18:
          instruction=new LoadInstruction(constants,Type.DOUBLE,buffer.get()&0xff);
          break;
        case 0x19:
          instruction=new LoadInstruction(constants,Type.REF,buffer.get()&0xff);
          break;
        case 0x1a:
        case 0x1b:
        case 0x1c:
        case 0x1d:
          instruction=new LoadInstruction(constants,Type.INT,b-0x1a);
          break;
        case 0x1e:
        case 0x1f:
        case 0x20:
        case 0x21:
          instruction=new LoadInstruction(constants,Type.LONG,b-0x1e);
          break;
        case 0x22:
        case 0x23:
        case 0x24:
        case 0x25:
          instruction=new LoadInstruction(constants,Type.FLOAT,b-0x24);
          break;
        case 0x26:
        case 0x27:
        case 0x28:
        case 0x29:
          instruction=new LoadInstruction(constants,Type.DOUBLE,b-0x26);
          break;
        case 0x2a:
        case 0x2b:
        case 0x2c:
        case 0x2d:
          instruction=new LoadInstruction(constants,Type.REF,b-0x2a);
          break;
        case 0x2e:
          instruction=new IaloadInstruction(constants);
          break;
        case 0x2f:
          instruction=new LaloadInstruction(constants);
          break;
        case 0x30:
          instruction=new FaloadInstruction(constants);
          break;
        case 0x31:
          instruction=new DaloadInstruction(constants);
          break;
        case 0x32:
          instruction=new AaloadInstruction(constants);
          break;
        case 0x33:
          instruction=new BaloadInstruction(constants);
          break;
        case 0x34:
          instruction=new CaloadInstruction(constants);
          break;
        case 0x35:
          instruction=new SaloadInstruction(constants);
          break;
        case 0x36:
          instruction=new IstoreInstruction(constants,buffer.get()&0xff);
          break;
        case 0x37:
          instruction=new LstoreInstruction(constants,buffer.get()&0xff);
          break;
        case 0x38:
          instruction=new FstoreInstruction(constants,buffer.get()&0xff);
          break;
        case 0x39:
          instruction=new DstoreInstruction(constants,buffer.get()&0xff);
          break;
        case 0x3a:
          instruction=new AstoreInstruction(constants,buffer.get()&0xff);
          break;
        case 0x3b:
        case 0x3c:
        case 0x3d:
        case 0x3e:
          instruction=new IstoreInstruction(constants,b-0x3b);
          break;
        case 0x3f:
        case 0x40:
        case 0x41:
        case 0x42:
          instruction=new LstoreInstruction(constants,b-0x3f);
          break;
        case 0x43:
        case 0x44:
        case 0x45:
        case 0x46:
          instruction=new FstoreInstruction(constants,b-0x43);
          break;
        case 0x47:
        case 0x48:
        case 0x49:
        case 0x4a:
          instruction=new DstoreInstruction(constants,b-0x47);
          break;
        case 0x4b:
        case 0x4c:
        case 0x4d:
        case 0x4e:
          instruction=new AstoreInstruction(constants,b-0x4b);
          break;
        case 0x4f:
          instruction=new IastoreInstruction(constants);
          break;
        case 0x50:
          instruction=new LastoreInstruction(constants);
          break;
        case 0x51:
          instruction=new FastoreInstruction(constants);
          break;
        case 0x52:
          instruction=new DastoreInstruction(constants);
          break;
        case 0x53:
          instruction=new AastoreInstruction(constants);
          break;
        case 0x54:
          instruction=new BastoreInstruction(constants);
          break;
        case 0x55:
          instruction=new CastoreInstruction(constants);
          break;
        case 0x56:
          instruction=new SastoreInstruction(constants);
          break;
        case 0x57:
          instruction=new PopInstruction(constants);
          break;
        case 0x58:
          instruction=new Pop2Instruction(constants);
          break;
        case 0x59:
          instruction=new DupInstruction(constants);
          break;
        case 0x5a:
          instruction=new DupX1Instruction(constants);
          break;
        case 0x5b:
          instruction=new DupX2Instruction(constants);
          break;
        case 0x5c:
          instruction=new Dup2Instruction(constants);
          break;
        case 0x5d:
          instruction=new Dup2X1Instruction(constants);
          break;
        case 0x5e:
          instruction=new Dup2X2Instruction(constants);
          break;
        case 0x5f: // TODO MISSING
          break;
        case 0x60:
          instruction=new IdyadInstruction(constants,Operator.ADD);
          break;
        case 0x61:
          instruction=new LdyadInstruction(constants,Operator.ADD);
          break;
        case 0x62:
          instruction=new FdyadInstruction(constants,Operator.ADD);
          break;
        case 0x63:
          instruction=new DdyadInstruction(constants,Operator.ADD);
          break;
        case 0x64:
          instruction=new IdyadInstruction(constants,Operator.SUB);
          break;
        case 0x65:
          instruction=new LdyadInstruction(constants,Operator.SUB);
          break;
        case 0x66:
          instruction=new FdyadInstruction(constants,Operator.SUB);
          break;
        case 0x67:
          instruction=new DdyadInstruction(constants,Operator.SUB);
          break;
        case 0x68:
          instruction=new IdyadInstruction(constants,Operator.MUL);
          break;
        case 0x69:
          instruction=new LdyadInstruction(constants,Operator.MUL);
          break;
        case 0x6a:
          instruction=new FdyadInstruction(constants,Operator.MUL);
          break;
        case 0x6b:
          instruction=new DdyadInstruction(constants,Operator.MUL);
          break;
        case 0x6c:
          instruction=new IdyadInstruction(constants,Operator.DIV);
          break;
        case 0x6d:
          instruction=new LdyadInstruction(constants,Operator.DIV);
          break;
        case 0x6e:
          instruction=new FdyadInstruction(constants,Operator.DIV);
          break;
        case 0x6f:
          instruction=new DdyadInstruction(constants,Operator.DIV);
          break;
        case 0x70:
          instruction=new IdyadInstruction(constants,Operator.REM);
          break;
        case 0x71:
          instruction=new LdyadInstruction(constants,Operator.REM);
          break;
        case 0x72:
          instruction=new FdyadInstruction(constants,Operator.REM);
          break;
        case 0x73:
          instruction=new DdyadInstruction(constants,Operator.REM);
          break;
        case 0x74:
          instruction=new InegInstruction(constants);
          break;
        case 0x75:
          instruction=new LnegInstruction(constants);
          break;
        case 0x76:
          instruction=new FnegInstruction(constants);
          break;
        case 0x77:
          instruction=new DnegInstruction(constants);
          break;
        case 0x78:
          instruction=new IdyadInstruction(constants,Operator.SHL);
          break;
        case 0x79:
          instruction=new LdyadInstruction(constants,Operator.SHL);
          break;
        case 0x7a:
          instruction=new IdyadInstruction(constants,Operator.SHR);
          break;
        case 0x7b:
          instruction=new LdyadInstruction(constants,Operator.SHR);
          break;
        case 0x7c:
          instruction=new IdyadInstruction(constants,Operator.USHR);
          break;
        case 0x7d:
          instruction=new LdyadInstruction(constants,Operator.USHR);
          break;
        case 0x7e:
          instruction=new IdyadInstruction(constants,Operator.AND);
          break;
        case 0x7f:
          instruction=new LdyadInstruction(constants,Operator.AND);
          break;
        case 0x80:
          instruction=new IdyadInstruction(constants,Operator.OR);
          break;
        case 0x81:
          instruction=new LdyadInstruction(constants,Operator.OR);
          break;
        case 0x82:
          instruction=new IdyadInstruction(constants,Operator.XOR);
          break;
        case 0x83:
          instruction=new LdyadInstruction(constants,Operator.XOR);
          break;
        case 0x84:
          instruction=new IincInstruction(constants,buffer.get()&0xff,buffer.get());
          break;
        case 0x85:
          instruction=new ConvertInstruction(constants,Conversion.I2L);
          break;
        case 0x86:
          instruction=new ConvertInstruction(constants,Conversion.I2F);
          break;
        case 0x87:
          instruction=new ConvertInstruction(constants,Conversion.I2D);
          break;
        case 0x88:
          instruction=new ConvertInstruction(constants,Conversion.L2I);
          break;
        case 0x89:
          instruction=new ConvertInstruction(constants,Conversion.L2F);
          break;
        case 0x8a:
          instruction=new ConvertInstruction(constants,Conversion.L2D);
          break;
        case 0x8b:
          instruction=new ConvertInstruction(constants,Conversion.F2I);
          break;
        case 0x8c:
          instruction=new ConvertInstruction(constants,Conversion.F2L);
          break;
        case 0x8d:
          instruction=new ConvertInstruction(constants,Conversion.F2D);
          break;
        case 0x8e:
          instruction=new ConvertInstruction(constants,Conversion.D2I);
          break;
        case 0x8f:
          instruction=new ConvertInstruction(constants,Conversion.D2L);
          break;
        case 0x90:
          instruction=new ConvertInstruction(constants,Conversion.D2F);
          break;
        case 0x91:
          instruction=new ConvertInstruction(constants,Conversion.I2B);
          break;
        case 0x92:
          instruction=new ConvertInstruction(constants,Conversion.I2C);
          break;
        case 0x93:
          instruction=new ConvertInstruction(constants,Conversion.I2S);
          break;
        case 0x94:
          instruction=new LcmpInstruction(constants);
          break;
        case 0x95:
          instruction=new FcmplInstruction(constants);
          break;
        case 0x96:
          instruction=new FcmpgInstruction(constants);
          break;
        case 0x97:
          instruction=new DcmplInstruction(constants);
          break;
        case 0x98:
          instruction=new DcmpgInstruction(constants);
          break;
        case 0x99:
          instruction=new IfInstruction(constants,Condition.EQ,buffer.getShort());
          break;
        case 0x9a:
          instruction=new IfInstruction(constants,Condition.NE,buffer.getShort());
          break;
        case 0x9b:
          instruction=new IfInstruction(constants,Condition.LT,buffer.getShort());
          break;
        case 0x9c:
          instruction=new IfInstruction(constants,Condition.GE,buffer.getShort());
          break;
        case 0x9d:
          instruction=new IfInstruction(constants,Condition.GT,buffer.getShort());
          break;
        case 0x9e:
          instruction=new IfInstruction(constants,Condition.LE,buffer.getShort());
          break;
        case 0x9f:
          instruction=new IfIcmpInstruction(constants,Condition.EQ,buffer.getShort());
          break;
        case 0xa0:
          instruction=new IfIcmpInstruction(constants,Condition.NE,buffer.getShort());
          break;
        case 0xa1:
          instruction=new IfIcmpInstruction(constants,Condition.LT,buffer.getShort());
          break;
        case 0xa2:
          instruction=new IfIcmpInstruction(constants,Condition.GE,buffer.getShort());
          break;
        case 0xa3:
          instruction=new IfIcmpInstruction(constants,Condition.GT,buffer.getShort());
          break;
        case 0xa4:
          instruction=new IfIcmpInstruction(constants,Condition.LE,buffer.getShort());
          break;
        case 0xa5:
          instruction=new IfAcmpInstruction(constants,Condition.EQ,buffer.getShort());
          break;
        case 0xa6:
          instruction=new IfAcmpInstruction(constants,Condition.NE,buffer.getShort());
          break;
        case 0xa7:
          instruction=new GotoInstruction(constants,buffer.getShort());
          break;
        case 0xa8:
          instruction=new JsrInstruction(constants,buffer.getShort());
          break;
        case 0xa9: //TODO ret
          break;
        case 0xaa:
          instruction=new TableswitchInstruction(constants,buffer);
          break;
        case 0xab: //TODO lookupswitch
          break;
        case 0xac:
          instruction=new IreturnInstruction(constants);
          break;
        case 0xad:
          instruction=new LreturnInstruction(constants);
          break;
        case 0xae:
          instruction=new FreturnInstruction(constants);
          break;
        case 0xaf:
          instruction=new DreturnInstruction(constants);
          break;
        case 0xb0:
          instruction=new AreturnInstruction(constants);
          break;
        case 0xb1:
          instruction=new ReturnInstruction(constants);
          break;
        case 0xb2:
          instruction=new GetstaticInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xb3:
          instruction=new PutstaticInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xb4:
          instruction=new GetfieldInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xb5:
          instruction=new PutfieldInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xb6:
          instruction=new InvokevirtualInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xb7:
          instruction=new InvokespecialInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xb8:
          instruction=new InvokestaticInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xb9:
          instruction=new InvokeinterfaceInstruction(constants,buffer.getShort()&0xffff,buffer.get()&0xff,buffer.get());
          break;
        case 0xba:
          instruction=new InvokedynamicInstruction(constants,buffer.getShort()&0xffff,buffer.get(),buffer.get());
          break;
        case 0xbb:
          instruction=new NewInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xbc:
          instruction=new NewarrayInstruction(constants,Type.values()[buffer.get()]);
          break;
        case 0xbd:
          instruction=new AnewarrayInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xbe:
          instruction=new ArraylengthInstruction(constants);
          break;
        case 0xbf:
          instruction=new AthrowInstruction(constants);
          break;
        case 0xc0:
          instruction=new CheckcastInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xc1:
          instruction=new InstanceofInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xc2:
          instruction=new MonitorEnterInstruction(constants);
          break;
        case 0xc3:
          instruction=new MonitorExitInstructions(constants);
          break;
        case 0xc4:
          int subCode=buffer.get();
          switch(subCode)
          {
            case 0x15:
              instruction=new LoadInstruction(constants,Type.INT,buffer.getShort()&0xffff);
              break;
            case 0x16:
              instruction=new LoadInstruction(constants,Type.LONG,buffer.getShort()&0xffff);
              break;
            case 0x17:
              instruction=new LoadInstruction(constants,Type.FLOAT,buffer.getShort()&0xffff);
              break;
            case 0x18:
              instruction=new LoadInstruction(constants,Type.DOUBLE,buffer.getShort()&0xffff);
              break;
            case 0x19:
              instruction=new LoadInstruction(constants,Type.REF,buffer.getShort()&0xffff);
              break;
            default:
              throw new RuntimeException(String.format("invalid wide sub opcode '%02x'",subCode));
          }
          break;
        case 0xc5: //TODO multianewarray
          break;
        case 0xc6:
          instruction=new IfInstruction(constants,Condition.NULL,buffer.getShort());
          break;
        case 0xc7:
          instruction=new IfInstruction(constants,Condition.NONNULL,buffer.getShort());
          break;
        case 0xc8:
          instruction=new GotoInstruction(constants,buffer.getInt());
          break;
        case 0xc9:
          instruction=new JsrInstruction(constants,buffer.getInt());
          break;
        case 0xca:
          instruction=new BreakpointInstruction(constants);
          break;
        case 0xfe:
        case 0xff:
          instruction=new ImdepInstruction(constants,b-0xfe);
          break;
        default:
          throw new RuntimeException(String.format("invalid opcode '%02x'",b));
      }
      instructions.add(instruction);
    }
    return instructions;
  }

}
