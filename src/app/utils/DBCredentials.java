package app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DBCredentials {
  public static Map<String,String> getAccessToken(){
    Map<String, String> res = new HashMap<String, String>();
      File configFile = new File("app/config.cnf");
      try {
          InputStream configFileStream = new FileInputStream(configFile);
          Properties p = new Properties();
          p.load(configFileStream);
          configFileStream.close();
          res.put("url", (String)p.get("db_url"));
          res.put("username", (String)p.get("db_username"));
          res.put("password", (String)p.get("db_password"));  
      } catch (Exception e) {  //IO or NullPointer exceptions possible in block above
          System.out.println("Useful message");
          System.exit(1);
      }
      return res;          
  }
}
