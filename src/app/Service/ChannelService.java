package app.Service;

import java.nio.channels.AsynchronousSocketChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import app.Model.Channel;
import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.Repository.ChannelRepository;
import app.Service.Interface.ServiceInterface;

public class ChannelService implements ServiceInterface {
  private ChannelRepository ChannelRepository;

  public ChannelService(Connection con) throws Exception{
    try{
      this.ChannelRepository = new ChannelRepository(con);
    }
    catch(SQLException e){
      throw e;
    }
  }
  public void createChannel(Request req, Response res) throws SQLException {
    System.out.println("Le client veut créer un channel !");
    String name = req.getParams().get("name");
    int adminUuid = Integer.parseInt(req.getParams().get("admin"));
        if(name == ""){
      res.setStatus(Type.ERROR);
      res.setObj("Il manque des informations, veuillez réessayer");
    }else{
      res.setStatus(Type.OK);
      Channel channel = ChannelRepository.insert(name, adminUuid);
      res.setObj(channel);
      res.setSendToAll(true);
    }
  }
  public void getAllChannels(Request req, Response res) throws SQLException {
    res.setStatus(Type.OK);
        List<Channel> channels = ChannelRepository.findEveryChannel();
    res.setObj(channels);
  }
  public void deleteChannel(Request req, Response res) throws SQLException {
    System.out.println("Le client veut supprimer un channel.");
    int cuid = Integer.parseInt(req.getParams().get("cuid"));
    int adminUuid = Integer.parseInt(req.getParams().get("admin"));
    boolean deleted = ChannelRepository.delete(cuid, adminUuid);  
    if(deleted){
      res.setStatus(Type.OK);
      res.setObj(cuid);
      res.setSendToAll(true);
    }else{
      res.setStatus(Type.ERROR);
      res.setObj("Il manque des informations, veuillez réessayer");
    }
  }
  public void run(Request req, Response res, AsynchronousSocketChannel client) throws SQLException {
    switch(req.getType()){
      case CREATE_CHANNEL:
        createChannel(req, res);  
        break;
      case GET_CHANNELS:
        getAllChannels(req, res);
        break;
      case DELETE_CHANNEL:
        deleteChannel(req, res);
        break;
      default:
        break;
    }
  } 
}
