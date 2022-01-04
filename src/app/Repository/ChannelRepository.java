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

  public ChannelRepository(Connection con) throws SQLException, ClassNotFoundException{
    super(con);
  }
  public Channel insert (String name, int adminUuid) throws SQLException{
    try{
      Statement stmt = con.createStatement();
      stmt.executeUpdate("INSERT INTO Channel (name,admin) VALUES ('"+name+"','"+adminUuid+"')");
      ResultSet res = stmt.executeQuery("Select max(cuid) from Channel");
      Channel channel = new Channel();
      if(res.next()){
        channel.setAdminUuid(adminUuid);
        channel.setCuid(res.getInt("MAX(cuid)"));
        channel.setName(name);
      }
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
  public void delete(int cuid, int adminUuid) throws SQLException{
    System.out.println("I am in delete() ");
    try {
      System.out.println("I am in delete() in try");
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("SELECT admin FROM Channel WHERE cuid='"+cuid+"'");
      if(res.getInt("admin")!= adminUuid){
        System.out.println("You don't have permission to perform this action");
      }else{
        stmt.executeUpdate("DELETE From Message WHERE channelId='"+cuid+"'");
        stmt.executeUpdate("DELETE FROM Channel cuid='"+cuid+"'");
      }
    }catch(SQLException e){
      throw e;
    }
  }
}
