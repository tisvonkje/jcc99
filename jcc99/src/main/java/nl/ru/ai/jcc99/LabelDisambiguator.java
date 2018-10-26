package nl.ru.ai.jcc99;

import java.util.HashMap;
import java.util.Map;

public class LabelDisambiguator
{
  private Map<String, Integer> lastInstanceNumber;
  private Map<String, String> translate;

  public LabelDisambiguator()
  {
    lastInstanceNumber=new HashMap<String,Integer>();
    translate=new HashMap<String,String>();
  }

  public String disambiguate(String label)
  {
    System.out.printf("called with label <%s>\n",label);
    String result=translate.get(label);
    if(result!=null)
      return result;
    //FIXME: HERE
    return null;
  }

}
