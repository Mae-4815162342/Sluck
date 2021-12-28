package app.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import app.Model.Message;

public class MessageRepository {
  private static Connection con;

  private MessageRepository() throws Exception{
    try{
      con = DriverManager.getConnection(
        "jdbc:mysql://gcvfvf8qih2d.eu-west-3.psdb.cloud/sluck?sslMode=VERIFY_IDENTITY",
        "yjmapqwhjf98", "pscale_pw_jg21Ifi5su45CMObtvAYeh_oMnN_vDa6BxSugcBuxdA");
    } catch (Exception e) {
      throw e;
    }
  }
  public Message insert (Message message) throws SQLException{
    try{
      Statement stmt = con.createStatement();
      stmt.executeUpdate("INSERT INTO Channel VALUES ('"+message.getMuid()+"','"+message.getMessage()+"','"+message.getUser().getUuid()+"','"+ message.getChannel().getCuid()+"')");

      return message;
    }
    catch(SQLException e){
      throw e; 
    }
  }
}
