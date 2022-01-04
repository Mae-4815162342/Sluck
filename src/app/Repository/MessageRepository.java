package app.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.Model.Message;
import app.Repository.Interface.RepositoryInterface;

public class MessageRepository extends RepositoryInterface{

  private static Connection con;

  public MessageRepository(Connection con) throws ClassNotFoundException, SQLException {
    super(con);
    this.con = con;
  }
  public static Message insert (int cuid, int uuid, String text) throws SQLException{
    try{
      Statement stmt = con.createStatement();
      stmt.executeUpdate("INSERT INTO Message (text,uuid,cuid) VALUES ('"+text+"',"+uuid+","+cuid+")");
      ResultSet res = stmt.executeQuery("Select max(muid) from Message");
      Message message = new Message();
      if(res.next()){
        message.setUuid(uuid);
        message.setMessage(text);
        message.setMuid(res.getInt("MAX(muid)"));
        message.setCuid(cuid);
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
      ResultSet res = stmt.executeQuery("select * from Message");
      while(res.next()){
        Message m = new Message();
        m.setCuid(res.getInt("cuid"));
        m.setUuid(res.getInt("uuid"));
        m.setMuid(res.getInt("muid"));
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
