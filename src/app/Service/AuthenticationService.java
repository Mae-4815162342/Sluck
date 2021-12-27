package app.Service;

import client.User;

public class AuthenticationService {
  private UserRepository UserRepository;

  public AuthenticationService(UserRepository UserRepository){
    this.UserRepository = UserRepository;
  }

  public User signin(Request request){
    String username;
    String password;
    if(username=="" || password==""){
      throw new Exception("Inputs can not be empty");
    }
    try{
      User user = UserRepository.findByUsernameAndPassword(username, password);

      return user;
    } catch (Exception e){
      throw new Exception("Could not authenticate user");
    }
  }
  public User signup(Request request){
    String username;
    String password;
    if(username=="" || password==""){
      throw new Exception("Inputs can not be empty");
    }
    try{
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      String pwd = DatatypeConverter.printHexBinary(md.digest()).toUpperCase();

      User user = new User();
      user.setPassword(pwd).setUsernamne(username).setUuid(UUID.randomUUID());

      UserRepository.insert(user);

      return user;
    } catch (Exception e){
      throw new Exception("Could not authenticate user");
    }
  }
}
