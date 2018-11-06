package nl.ru.ai.jcc99;

import nl.ru.ai.jcc99.constants.OutlineConstant;

public interface Coder
{
  short getWordSize();
  String getVersion();
  void close();
  void codeEntry(Method method);
  void codeLabel(Method method);
  void codeLabel(OutlineConstant constant);
  void codeLabel(String string);
  void codeComment(String version);
  void codeLink(int number);
  void codeLoad(int parameterUnits, int local);
  void codeDload(int parameterUnits, int local);
  void codeAddInt();
  void codeAndInt();
  void codeStore(int parameterUnits, int local);
  void codeDStore(int parameterUnits, int local);
  void codeReturnSingle(int parameterUnits);
  void codePushInt(int value);
  void codeCall(Method method);
  void codeReturn();
  void codePushAddress(OutlineConstant constant);
  void codeData();
  void codeAsciz(String string);
  void codeAllocateArray(TypeSuffix elementType);
  void codePushByte(int value);
  void codeArrayStore(TypeSuffix elementType);
  void codeArrayLoad(TypeSuffix elementType);
  void codeAllocateObject(int size);
  void codeDup();
  void codePutField(int offset);
  void codeHeap();
  void codeArrayLength();
  void codeGetField(int offset);
  void codeJump(String label);
}
