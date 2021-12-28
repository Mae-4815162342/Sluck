package app.Repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import app.Model.Channel;

public class ChannelRepository {
  private static Connection con;

  private ChannelRepository() throws Exception{
    try{
      con = DriverManager.getConnection(
        "jdbc:mysql://gcvfvf8qih2d.eu-west-3.psdb.cloud/sluck?sslMode=VERIFY_IDENTITY",
        "yjmapqwhjf98", "pscale_pw_jg21Ifi5su45CMObtvAYeh_oMnN_vDa6BxSugcBuxdA");
    } catch (Exception e) {
      throw e;
    }
  }
  public Channel insert (Channel channel) throws SQLException{
    try{
      Statement stmt = con.createStatement();
      stmt.executeUpdate("INSERT INTO Channel VALUES ('"+channel.getCuid()+"','"+channel.getName()+"','"+channel.getAdmin().getUuid()+"')");

      return channel;
    }
    catch(SQLException e){
      throw e; 
    }
  }
}
