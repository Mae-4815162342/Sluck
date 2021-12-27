package app.Repo;

import java.sql.Connection;

public static class UserRepository {
  
  private static Connection con;

  private UserRepository(){
    try{
      con = DriverManager.getConnection(
        "jdbc:mysql://gcvfvf8qih2d.eu-west-3.psdb.cloud/sluck?sslMode=VERIFY_IDENTITY",
        "yjmapqwhjf98", "pscale_pw_jg21Ifi5su45CMObtvAYeh_oMnN_vDa6BxSugcBuxdA");
    } catch (Exception e) {
      throw e;
    }
  }
  public static User findByUsernameAndPassword(String username, String password){
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(password.getBytes());
    String pwd = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();

    Statement stmt = con.createStatement();
    ResultSet res = stmt.executeQuery("select * from User where username='"+ username + "' and password='"+ pwd +"'");

    if(!res){
      throw new Exception("Could not login into account");
    }

    User user = new User();
    user.setPassword(res.getString("password")).setUsername(res.getString("username")).setUuid(res.getString("uuid"));

    return user;

  }

  public static User insert(User user) {


    Statement stmt = con.createStatement();
    ResultSet res = stmt.executeUpdate("INSERT INTO User VALUES ('"+user.getUuid()+"','"+user.getUsername()+"','"+pwd+"')");

    return user;
  }
}
