package app.router;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.Map;

import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.Service.AuthenticationService;
import app.Service.ChannelService;
import app.Service.MessagingService;
import app.Service.Interface.ServiceInterface;

public class Router {
  private Map<Type, ServiceInterface> services;
  public Router() throws Exception{
    services = new HashMap<Type, ServiceInterface>();
    AuthenticationService auth = new AuthenticationService();
    services.put(Type.SIGNIN,auth);
    services.put(Type.SIGNUP, auth);
    services.put(Type.SIGNOUT, auth);
    services.put(Type.EXIT, auth);

    ChannelService channel = new ChannelService();
    services.put(Type.GET_CHANNELS,channel);
    services.put(Type.CREATE_CHANNEL, channel);

    MessagingService message = new MessagingService();
    services.put(Type.SEND_MESSAGES,message);
    services.put(Type.GET_USERS,message);
    services.put(Type.ANY,message);
  }
  
  public void run(Request req, Response res, AsynchronousSocketChannel client) 
    throws IOException{
      ServiceInterface action = services.get(req.getType());
      action.run(req, res, client);
  }
}
