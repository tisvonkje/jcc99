package nl.ru.ai.jcc99;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LabelDisambiguator
{
  private Map<String, Integer> lastInstanceNumber;
  private Map<String, String> translate;
  private MessageDigest md5;

  public LabelDisambiguator() throws NoSuchAlgorithmException
  {
    md5=MessageDigest.getInstance("MD5");
    lastInstanceNumber=new HashMap<String, Integer>();
    translate=new HashMap<String, String>();
  }

  public String name(Method method)
  {
    System.out.printf("called with label <%s>\n",method);
    String name=method.getFullName();
    /*
     * Already translated before? return it
     */
    String result=translate.get(name);
    if(result!=null)
      return result;
    /*
     * Native methods are special, we cannot create dynamic names here, so we fall back to a hash
     */
    if(method.isNative())
    {
      byte[] bytesOfMessage;
      try
      {
        bytesOfMessage=name.getBytes("UTF-8");
        byte[] theDigest = md5.digest(bytesOfMessage);
        StringBuffer buffer=new StringBuffer();
        buffer.append('l');
        for (int i = 0; i < theDigest.length; ++i) 
          buffer.append(Integer.toHexString((theDigest[i] & 0xFF) | 0x100).substring(1,3));
        result=buffer.substring(0,7);
      }
      catch(UnsupportedEncodingException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else
    {
      /*
       * First occurrence of the label, extract suggested short version
       */
      int start=name.indexOf('.');
      int end=name.indexOf(':');
      if(start<0||end<0)
        throw new RuntimeException("Illegal label name '"+method+"'");
      String suggestion=name.substring(start+1,end);
      /*
       * Suggested version already given?
       */
      Integer instance=lastInstanceNumber.get(suggestion);
      if(instance==null)
        instance=0;
      else
        instance++;
      lastInstanceNumber.put(suggestion,instance);
      result=instance==0 ? suggestion : suggestion+instance;
    }
    translate.put(name,result);
    return result;
  }

}
