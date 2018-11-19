package nl.ru.ai.jcc99;

public class LabelDisambiguator
{

  public String name(Method method)
  {
    String namePart=method.getClassFile().getName()+"/"+method.getName();
    String descriptorPart=method.getDescriptor();
    return "Method_"+mangle(namePart+"/"+descriptorPart);
  }

  private String mangle(String string)
  {
    string=string.replace('_','!');
    string=string.replace('/','_');
    string=string.replace("<","_d_");
    string=string.replace(">","_b_");
    string=string.replace("(","_q_");
    string=string.replace(")","_p_");
    string=string.replace("[","_a_");
    string=string.replace(";","_s_");
    string=string.replace("!","_u_");
    return string;
  }

  public String name(Field field)
  {
    String namePart=field.getClassFile().getName()+"/"+field.getName();
    return "Field_"+mangle(namePart);
  }

}
