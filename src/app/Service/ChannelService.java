package app.Service;

import java.nio.channels.AsynchronousSocketChannel;
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

  public ChannelService() throws Exception{
    try{
      this.ChannelRepository = new ChannelRepository();
    }
    catch(SQLException e){
      throw e;
    }
  }
  public void createChannel(Request req, Response res) throws SQLException {
    res.setStatus(Type.OK);
    res.setType(req.getType());
    String name = req.getParams().get("name");
    int adminUuid = Integer.parseInt(req.getParams().get("admin"));
    Channel channel = ChannelRepository.insert(name, adminUuid);
    res.setObj(channel);
    res.setSendToAll(true);
  }
  public void getAllChannels(Request req, Response res) throws SQLException {
    res.setStatus(Type.OK);
    res.setType(req.getType());
    List<Channel> channels = ChannelRepository.findEveryChannel();
    res.setObj(channels);
  }
  public void run(Request req, Response res, AsynchronousSocketChannel client) throws SQLException {
    switch(req.getType()){
      case CREATE_CHANNEL:
        createChannel(req, res);  
        break;
      case GET_CHANNELS:
        getAllChannels(req, res);
        break;
      default:
        break;
    }
  }
  
}
