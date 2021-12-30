package app.Service;

import java.util.UUID;

import app.Model.User;
import app.Repository.UserRepository;
public class AuthenticationService {
  private UserRepository UserRepository;

  public AuthenticationService(UserRepository UserRepository){
    this.UserRepository = UserRepository;
  }

  public User signin(Request request) throws Exception{
    String username;
    String password;
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
    String username;
    String password;
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
}
