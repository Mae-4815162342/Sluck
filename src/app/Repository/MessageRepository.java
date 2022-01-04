package app.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import app.Model.Message;
import app.Repository.Interface.RepositoryInterface;

public class MessageRepository extends RepositoryInterface{

  public MessageRepository(Connection con) throws ClassNotFoundException, SQLException {
    super(con);
  }
  public Message insert (int cuid, String username, String text) throws SQLException{
    try{
      Statement stmt = con.createStatement();
      String modifiedText = text.replaceAll("'", "''");
      stmt.executeUpdate("INSERT INTO Message (text, username, cuid) VALUES ('"+modifiedText+"','"+username+"',"+cuid+")");
      ResultSet res = stmt.executeQuery("Select max(muid) from Message");
      Message message = new Message();
      if(res.next()){
        message.setMessage(text);
        message.setMuid(res.getInt("MAX(muid)"));
        message.setCuid(cuid);
        message.setUsername(username);
      }
      return message;
    }
    catch(SQLException e){
      throw e; 
    }
  }
  public List<Message> findEveryMessage() throws SQLException {
    List<Message> messages = new ArrayList<Message>();
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("SELECT * from Message");
      while(res.next()){
        Message m = new Message();
        m.setCuid(res.getInt("cuid"));
        m.setMuid(res.getInt("muid"));
        m.setUsername(res.getString("username"));
        m.setMessage(res.getString("text"));
        messages.add(m);
      }
    }
    catch(SQLException e){
      throw e;
    }
    return messages;
  }

}
