package app.Repository;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.Model.Channel;
import app.Model.Message;
import app.Model.User;

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
  public List<Channel> findEveryChannel(UserRepository UserRepository, MessageRepository MessageRepository) throws SQLException, NoSuchAlgorithmException, Exception {
    List<Channel> channels = new ArrayList<Channel>();
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("select * from Channel");
      while(res.next()){
        Channel c = new Channel(res.getString("Name"),null, res.getInt("cuid"));
        User admin = UserRepository.findById(res.getInt("admin"));
        List<Message> messages = MessageRepository.findByChannelId(res.getInt("cuid"), c, UserRepository);
        c.setAdmin(admin);
        c.setMessages(messages);
        channels.add(c);
      }
    }
    catch(SQLException e){
      throw e;
    }
    return channels;
  }
}
