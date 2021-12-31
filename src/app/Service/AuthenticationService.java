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

  public User signin(Request request) throws Exception{
    String username = request.getParams().get("username");
    String password = request.getParams().get("password");
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
  public User signup(Request request) throws Exception{
    String username = request.getParams().get("username");
    String password = request.getParams().get("password");
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
  public void exit(AsynchronousSocketChannel client) throws IOException{
    App.clients.remove(client);
    System.out.println("Clients restants :");
    for(AsynchronousSocketChannel cli : App.clients){
      System.out.println(cli.getLocalAddress());
    }
  }
  @Override
  public void run(Request req, Response res, AsynchronousSocketChannel client) throws IOException {
    Type type = req.getType();
    if(type.equals(Type.EXIT)){
      System.out.println("Un client s'est déconnecté depuis " + client.getLocalAddress());
      exit(client);
    }
  }
}
