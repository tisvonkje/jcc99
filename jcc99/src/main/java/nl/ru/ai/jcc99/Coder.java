package nl.ru.ai.jcc99;

public interface Coder
{
  short getWordSize();
  String getVersion();
  void codeEntry(Method method);
  void codeLabel(Method method);
  void codeComment(String version);
  void codeLink(int number);
}
