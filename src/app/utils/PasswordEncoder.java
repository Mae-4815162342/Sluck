package app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
  public static String encode(String password) throws NoSuchAlgorithmException{
    try{
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      byte[] bytes = md.digest();  
      String pwd = null;

      StringBuilder s = new StringBuilder();  
      for(int i=0; i< bytes.length ;i++)  {  
          s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
      }  
      pwd = s.toString();
      return pwd;
    }
    catch(NoSuchAlgorithmException e){
      throw e;
    }
  }
}
