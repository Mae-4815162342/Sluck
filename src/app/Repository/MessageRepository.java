package app.Repository;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.Model.Channel;
import app.Model.Message;
import app.Model.User;
import app.Repository.Interface.RepositoryInterface;

public class MessageRepository extends RepositoryInterface{
  private static Connection con;

  private MessageRepository() throws ClassNotFoundException, SQLException {
    super();
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
  public List<Message> findByChannelId(int cuid, Channel channel, UserRepository UserRepository) throws SQLException, NoSuchAlgorithmException, Exception{
    List<Message> messages = new ArrayList<Message>();
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("select * from Message where userId=" + cuid);
      while(res.next()){
        Message m = new Message(res.getInt("muid"),null, res.getString("text"));
        User user = UserRepository.findById(res.getInt("userId"));
        m.setUser(user);
      }
    }
    catch(SQLException e){
      throw e;
    }
    return messages;
  }
}
