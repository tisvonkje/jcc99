package nl.ru.ai.jcc99;

import nl.ru.ai.jcc99.attributes.CodeAttribute;
import nl.ru.ai.jcc99.constants.OutlineConstant;
import nl.ru.ai.jcc99.instructions.Condition;

public interface Coder
{
  short getWordSize();
  String getVersion();
  void close();
  void codeEntry();
  void codePrepare();
  void codeExit();
  void codeLabel(Method method);
  void codeDebugLabel(Method method);
  void codeLabel(OutlineConstant constant);
  void codeLabel(String prefix, ClassFile classFile);
  void codeLabel(String string);
  void codeComment(String version);
  void codeLink(int number);
  void codeLoad(int parameterUnits, int local);
  void codeIntInc(int parameterUnits, int local, int value);
  void codeDload(int parameterUnits, int local);
  void codeAddInt();
  void codeSubInt();
  void codeDivInt();
  void codeModInt();
  void codeMulInt();
  void codeAndInt();
  void codeOrInt();
  void codeStore(int parameterUnits, int local);
  void codeDStore(int parameterUnits, int local);
  void codeReturnSingle(int parameterUnits);
  void codeReturn(int parameterUnits);
  void codePushInt(int value);
  void codePush(Field field);
  void codePop(Field field);
  void codeCall(Method method);
  void codeDynamicCall(Method method);
  void codePushAddress(OutlineConstant constant);
  void codeData();
  void codeAsciz(String string);
  void codeAllocateArray(TypeSuffix elementType);
  void codePushByte(int value);
  void codeArrayStore(TypeSuffix elementType);
  void codeArrayLoad(TypeSuffix elementType);
  void codeAllocateObject(int size, ClassFile classFile);
  void codeDup();
  void codePutField(int offset);
  void codeHeap();
  void codeArrayLength();
  void codeGetField(int offset);
  void codeJump(String label);
  void codeIntToByte();
  void codeIntToChar();
  void codeIntCompare(Condition condition, String label);
  void codeZeroCompare(Condition condition, String label);
  void codeBss(Field field);
  void codeShiftRightInt();
  void codeShiftUnsignedRightInt();
  void codeShiftLeftInt();
  void codeAlignWord();
  void codeWord(String string);
  void codeWord(int value);
  void codeWord(Method method);
  void codeChar(char charAt);
  void codeNegInt();
  void codeDupx1();
  void codePop();
  void codeCall(String string);
  void codeWord(String prefix,ClassFile classFile);
  void codeRefCompare(Condition condition, String label);
  void codeInstanceOf(ClassFile classFile);
}
