package nl.ru.ai.jcc99;

public interface Coder
{
  void codeEntry(Method method);
  void codeLabel(Method method);
  String getVersion();
  void codeComment(String version);
}
