package app.Repository;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.Model.Channel;
import app.Repository.Interface.RepositoryInterface;

public class ChannelRepository extends RepositoryInterface{
  private static Connection con;

  public ChannelRepository() throws SQLException, ClassNotFoundException{
    super();
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
