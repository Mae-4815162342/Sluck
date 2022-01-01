package app.Repository;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.Model.Channel;

public class ChannelRepository {
  private static Connection con;

  public ChannelRepository() throws Exception{
    try{
      /* Class.forName("com.mysql.cj.jdbc.Driver");
      con = DriverManager.getConnection(
      "jdbc:mysql://gcvfvf8qih2d.eu-west-3.psdb.cloud/sluck?sslMode=VERIFY_IDENTITY",
      "r29yq8cim0hr", "pscale_pw_4HBD30cCvDZB54mf-H3ESoo3NrCr3n670yxGz28I_VI"); */
      con = null;
    } catch (Exception e) {
      throw e;
    }
  }
  public Channel insert (String name, int adminUuid) throws SQLException{
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("INSERT INTO Channel (name,admin) OUTPUT VALUES ('"+name+"','"+adminUuid+"'); Select max(cuid) from Channel ");
      Channel channel = new Channel();
      channel.setAdminUuid(adminUuid);
      channel.setCuid(res.getInt("MAX(cuid)"));
      channel.setName(name);
      return channel;
    }
    catch(SQLException e){
      throw e; 
    }
  }
  public List<Channel> findEveryChannel() throws SQLException {
    List<Channel> channels = new ArrayList<Channel>();
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("select * from Channel");
      while(res.next()){
        Channel c = new Channel();
        c.setAdminUuid(res.getInt("admin"));
        c.setCuid(res.getInt("cuid"));
        c.setName(res.getString("name"));
        channels.add(c);
      }
    }
    catch(SQLException e){
      throw e;
    }
    return channels;
  }
}
