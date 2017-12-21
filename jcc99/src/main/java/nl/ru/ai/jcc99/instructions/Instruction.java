package nl.ru.ai.jcc99.instructions;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

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

  public static List<Instruction> create(Constant [] constants, ByteBuffer buffer)
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
        case 0x0e:
        case 0x0f:
          instruction=new DconstInstruction(constants,(double)(b-0x0e));
          break;
        case 0x10:
          instruction=new IpushInstruction(constants,buffer.get());
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
          instruction=new IloadInstruction(constants,buffer.get()&0xff);
          break;
        case 0x19:
          instruction=new AloadInstruction(constants,buffer.get()&0xff);
          break;
        case 0x1a:
        case 0x1b:
        case 0x1c:
        case 0x1d:
          instruction=new IloadInstruction(constants,b-0x1a);
          break;
        case 0x1e:
        case 0x1f:
        case 0x20:
        case 0x21:
          instruction=new LloadInstruction(constants,b-0x1e);
          break;
        case 0x22:
        case 0x23:
        case 0x24:
        case 0x25:
          instruction=new FloadInstruction(constants,b-0x24);
          break;
        case 0x26:
        case 0x27:
        case 0x28:
        case 0x29:
          instruction=new DloadInstruction(constants,b-0x26);
          break;
        case 0x2a:
        case 0x2b:
        case 0x2c:
        case 0x2d:
          instruction=new AloadInstruction(constants,b-0x2a);
          break;
        case 0x2e:
          instruction=new IaloadInstruction(constants);
          break;
        case 0x32:
          instruction=new AaloadInstruction(constants);
          break;
        case 0x33:
          instruction=new BaloadInstruction(constants);
          break;
        case 0x36:
          instruction=new IstoreInstruction(constants,buffer.get()&0xff);
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
        case 0x53:
          instruction=new AastoreInstruction(constants);
          break;
        case 0x56:
          instruction=new SastoreInstruction(constants);
          break;
        case 0x57:
          instruction=new PopInstruction(constants);
          break;
        case 0x59:
          instruction=new DupInstruction(constants);
          break;
        case 0x60:
          instruction=new IdyadInstruction(constants,Operator.ADD);
          break;
        case 0x64:
          instruction=new IdyadInstruction(constants,Operator.SUB);
          break;
        case 0x68:
          instruction=new IdyadInstruction(constants,Operator.MUL);
          break;
        case 0x6c:
          instruction=new IdyadInstruction(constants,Operator.DIV);
          break;
        case 0x70:
          instruction=new IdyadInstruction(constants,Operator.REM);
          break;
        case 0x78:
          instruction=new IdyadInstruction(constants,Operator.SHL);
          break;
        case 0x7a:
          instruction=new IdyadInstruction(constants,Operator.SHR);
          break;
        case 0x7e:
          instruction=new IdyadInstruction(constants,Operator.AND);
          break;
        case 0x80:
          instruction=new IdyadInstruction(constants,Operator.OR);
          break;
        case 0x82:
          instruction=new IdyadInstruction(constants,Operator.XOR);
          break;
        case 0x84:
          instruction=new IincInstruction(constants,buffer.get()&0xff,buffer.get());
          break;
        case 0x85:
          instruction=new IconvertInstruction(constants,Conversion.I2L);
          break;
        case 0x86:
          instruction=new IconvertInstruction(constants,Conversion.I2F);
          break;
        case 0x87:
          instruction=new IconvertInstruction(constants,Conversion.I2D);
          break;
        case 0x91:
          instruction=new IconvertInstruction(constants,Conversion.I2B);
          break;
        case 0x92:
          instruction=new IconvertInstruction(constants,Conversion.I2C);
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
        case 0xa7:
          instruction=new GotoInstruction(constants,buffer.getShort());
          break;
        case 0xaa:
          instruction=new TableswitchInstruction(constants,buffer);
          break;
        case 0xac:
          instruction=new IreturnInstruction(constants);
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
          instruction=new Invokeinterface(constants,buffer.getShort()&0xffff,buffer.get()&0xff,buffer.get());
          break;
        case 0xbb:
          instruction=new NewInstruction(constants,buffer.getShort()&0xffff);
          break;
        case 0xbc:
          instruction=new NewarrayInstruction(constants,buffer.get());
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
        case 0xc6:
          instruction=new IfInstruction(constants,Condition.NULL,buffer.getShort());
          break;
        case 0xc7:
          instruction=new IfInstruction(constants,Condition.NON_NULL,buffer.getShort());
          break;
        default:
          throw new RuntimeException(String.format("invalid opcode '%02x'",b));
      }
      instructions.add(instruction);
    }
    return instructions;
  }

}
