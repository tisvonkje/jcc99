package nl.ru.ai.jcc99;

public interface Coder
{
  short getWordSize();
  String getVersion();
  void close();
  void codeEntry(Method method);
  void codeLabel(Method method);
  void codeComment(String version);
  void codeLink(int number);
  void codeLoad(int parameterUnits, int local);
  void codeDload(int parameterUnits, int local);
  void codeAddInt();
  void codeStore(int parameterUnits, int local);
  void codeDStore(int parameterUnits, int local);
  void codeReturnSingle(int parameterUnits);
}
