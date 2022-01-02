package app.Service;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import app.Model.Message;
import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.Repository.MessageRepository;
import app.Service.Interface.ServiceInterface;

public class MessagingService implements ServiceInterface {
  private MessageRepository messageRepository;
  
  public MessagingService(Connection con) throws ClassNotFoundException, SQLException{
    messageRepository = new MessageRepository(con);
  }
  public void getAllMessages(Request req, Response res) throws SQLException{
    List<Message> channels = messageRepository.findEveryMessage();
    res.setStatus(Type.OK);
        res.setObj(channels);
  }
  public void sendMessage(Request req, Response res) throws SQLException{
    String text = req.getParams().get("message");
    int cuid = Integer.parseInt(req.getParams().get("cuid"));
    int uuid = Integer.parseInt(req.getParams().get("uuid"));
    if(text==null){
      res.setStatus(Type.ERROR);
      res.setObj("Il manque des informations, veuillez réessayer");
    }
    else{
      res.setStatus(Type.OK);
      Message message = messageRepository.insert(cuid, uuid, text);
      res.setObj(message);
      res.setSendToAll(true);
    }
  }
  public void echoMessage(Request req, Response res) throws IOException{
    res.setStatus(Type.OK);   
    String resp = "Le server a bien reçu : " + req.getParams().get("message");
    res.setObj(resp);
    res.setType(Type.ANY);   
  }
  public void run(Request req, Response res, AsynchronousSocketChannel client) 
    throws SQLException, IOException {
      switch(req.getType()){
        case ANY:
          System.out.println("On va lui renvoyer son message !");
          echoMessage(req, res);
          break;
        case CREATE_MESSAGE:
          System.out.println("On envoie a tout le monde ce message !");
          sendMessage(req, res);
          break;
        case GET_MESSAGES:
          System.out.println("On va lui filer tous les messages du serveur");
          getAllMessages(req, res);
          break;
        default:
          break;
      }
  }
}
