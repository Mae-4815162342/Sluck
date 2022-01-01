package app.Service;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.sql.SQLException;

import app.App;
import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.Model.User;
import app.Repository.UserRepository;
import app.Service.Interface.ServiceInterface;
public class AuthenticationService implements ServiceInterface {
  private UserRepository UserRepository;

  public AuthenticationService() throws Exception{
    try{
      this.UserRepository = new UserRepository();
    }
    catch(SQLException e){
      throw e;
    }
  }

  public User signin(Request req) throws Exception{
    String username = req.getParams().get("username");
    String password = req.getParams().get("password");
    if(username=="" || password==""){
      throw new Exception("Inputs can not be empty");
    }
    try{
      app.Model.User user = UserRepository.findByUsernameAndPassword(username, password);

      return user;
    } catch (Exception e){
      throw new Exception("Could not authenticate user");
    }
  }
  public User signup(Request req) throws Exception{
    String username = req.getParams().get("username");
    String password = req.getParams().get("password");
    if(username=="" || password==""){
      throw new Exception("Inputs can not be empty");
    }
    try{
      User user = new User();
      user.setPassword(password);
      user.setUsername(username);

      user = UserRepository.insert(user);

      return user;
    } catch (Exception e){
      throw new Exception("Could not authenticate user");
    }
  }
  public void exit(Request req, Response res, AsynchronousSocketChannel client) throws IOException{
    App.clients.remove(client);
    System.out.println("Clients restants :");
    for(AsynchronousSocketChannel cli : App.clients){
      System.out.println(cli.getRemoteAddress());
    }
    res.setStatus(Type.OK);
    res.setType(req.getType());
    res.setSendToAll(true);
    res.setObj(client.getRemoteAddress().toString());
  }
  public void run(Request req, Response res, AsynchronousSocketChannel client) throws IOException {
    switch(req.getType()){
      case SIGNIN:
        break;
      case SIGNUP:
        break;
      case SIGNOUT:
        System.out.println("Un user a quitté sa session depuis " + client.getRemoteAddress());
      break;
      case EXIT:
        System.out.println("Un client s'est déconnecté depuis " + client.getRemoteAddress());
        exit(req, res, client);
        break;
      default:
        break;
    }
  }
}
