package app.router;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.Service.AuthenticationService;
import app.Service.ChannelService;
import app.Service.MessagingService;
import app.Service.Interface.ServiceInterface;
import app.utils.DBCredentials;

public class Router {
  private Map<Type, ServiceInterface> services;
  public Router() throws Exception{
    Connection con = null;
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Map<String,String> credentials = DBCredentials.getAccessToken();
      con = DriverManager.getConnection(credentials.get("url"),credentials.get("username"), credentials.get("password"));
    } catch (SQLException e) {
      throw e;
    }
    services = new HashMap<Type, ServiceInterface>();
    AuthenticationService auth = new AuthenticationService(con);
    services.put(Type.SIGNIN,auth);
    services.put(Type.SIGNUP, auth);
    services.put(Type.SIGNOUT, auth);
    services.put(Type.EXIT, auth); // DONE
    services.put(Type.GET_USERS,auth); // DONE

    ChannelService channel = new ChannelService(con);
    services.put(Type.GET_CHANNELS,channel); //DONE
    services.put(Type.CREATE_CHANNEL, channel); //DONE

    MessagingService message = new MessagingService(con);
    services.put(Type.GET_MESSAGES,message); //DONE
    services.put(Type.CREATE_MESSAGE,message); //DONE
    services.put(Type.ANY,message); // DONE
  }
  
  public void run(Request req, Response res, AsynchronousSocketChannel client) 
    throws IOException, SQLException, NoSuchAlgorithmException{
      ServiceInterface action = services.get(req.getType());
      action.run(req, res, client);
  }
}
