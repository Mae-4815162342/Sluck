package app.Service;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.App;
import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.Model.User;
import app.Repository.UserRepository;
import app.Service.Interface.ServiceInterface;
public class AuthenticationService implements ServiceInterface {
  private UserRepository UserRepository;

  public AuthenticationService(Connection con) throws Exception{
    try{
      this.UserRepository = new UserRepository(con);
    }
    catch(SQLException e){
      throw e;
    }
  }

  public void signin(Request req, Response res, AsynchronousSocketChannel client) throws NoSuchAlgorithmException, SQLException{
    String username = req.getParams().get("username");
    String password = req.getParams().get("password");
    if(username=="" || password==""){
      res.setStatus(Type.ERROR);
      res.setObj("Il manque des informations, veuillez réessayer");
    } else{
      User user = UserRepository.findByUsernameAndPassword(username, password);
      if(user == null){
        res.setStatus(Type.ERROR);
        res.setObj("Pas de user avec ces identifiants, veuillez réessayer");
      }
      else{
        if(App.users.containsValue(user)){
          res.setStatus(Type.ERROR);
          res.setObj("Un client est déjà connecté avec ces identifiants ! Veuillez réessayer");
        }
        else{
          res.setStatus(Type.OK);
          App.users.put(client, user);
          res.setObj(user);
          res.setSendToAll(true);
        }
      }
    }
  }
  public void signup(Request req, Response res, AsynchronousSocketChannel client) throws NoSuchAlgorithmException, SQLException{
    String username = req.getParams().get("username");
    String password = req.getParams().get("password");
        if(username=="" || password==""){
      res.setStatus(Type.ERROR);
      res.setObj("Il manque des informations, veuillez réessayer");
    }
    else{
      User user = UserRepository.insert(username, password);
      if(user == null){
        res.setStatus(Type.ERROR);
        res.setObj("Un user avec ces identifiants existe déjà, veuillez vous connecter ou créer un nouveau compte");
      }
      else{
        res.setStatus(Type.OK);
        App.users.put(client, user);
        res.setObj(user);
        res.setSendToAll(true);
      }
    }
  }
  public void signout(Request req, Response res, AsynchronousSocketChannel client) throws NoSuchAlgorithmException, SQLException{
    res.setObj(App.users.get(client));
    App.users.remove(client);
    System.out.println("Users restants :");
    for (Map.Entry<AsynchronousSocketChannel, User> cli : App.users.entrySet()) {
      System.out.println(cli.getValue().getUsername());
    }
    res.setStatus(Type.OK);
        res.setSendToAll(true);
  }
  public void getAllUsers(Request req, Response res) throws IOException{
    res.setStatus(Type.OK);
    List<User> list = new ArrayList<User>();
    for (Map.Entry<AsynchronousSocketChannel, User> cli : App.users.entrySet()) {
      list.add(cli.getValue());
    }
    res.setObj(list);
      }
  public void exit(Request req, Response res, AsynchronousSocketChannel client) throws IOException{
    App.clients.remove(client);
    System.out.println("Clients restants :");
    for(AsynchronousSocketChannel cli : App.clients){
      System.out.println(cli.getRemoteAddress());
    }
    res.setStatus(Type.OK);
      }
  public void run(Request req, Response res, AsynchronousSocketChannel client) throws IOException, NoSuchAlgorithmException, SQLException {
    switch(req.getType()){
      case SIGNIN:
        System.out.println("Un client veut se connecter !");
        signin(req, res, client);
        break;
      case SIGNUP:
        System.out.println("Un client veut s'inscrire !");
        signup(req, res, client);
        break;
      case SIGNOUT:
        System.out.println("Un user a quitté sa session depuis " + client.getRemoteAddress());
        signout(req, res, client);
        break;
      case GET_USERS:
        System.out.println("On va filer à tout le monde la liste des clients !"); // Ne pas filer à tout le monde
        getAllUsers(req, res);
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
