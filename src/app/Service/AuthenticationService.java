package app.Service;

import java.nio.channels.AsynchronousSocketChannel;
import java.sql.SQLException;
import java.util.UUID;

import app.Model.Request;
import app.Model.Response;
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
      user.setUuid(UUID.randomUUID());

      UserRepository.insert(user);

      return user;
    } catch (Exception e){
      throw new Exception("Could not authenticate user");
    }
  }

  @Override
  public void run(Request req, Response res, AsynchronousSocketChannel client) {
    // TODO Auto-generated method stub


  }
}
