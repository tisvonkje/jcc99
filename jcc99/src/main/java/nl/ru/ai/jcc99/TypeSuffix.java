package nl.ru.ai.jcc99;

public enum TypeSuffix {
  UNUSED0, UNUSED1, UNUSED2, UNUSED3, BOOLEAN, CHAR, FLOAT, DOUBLE, BYTE, SHORT, INT, LONG, REF;

  public String toString()
  {
    switch(this)
    {
      case BOOLEAN:
        return "b";
      case CHAR:
        return "c";
      case FLOAT:
        return "f";
      case DOUBLE:
        return "d";
      case SHORT:
        return "s";
      case INT:
        return "i";
      case LONG:
        return "l";
      case REF:
        return "a";
      default:
        throw new RuntimeException(String.format("Invalid type '%s'",this));
    }
  }
}
