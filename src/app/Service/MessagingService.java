package app.Service;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.List;

import app.App;
import app.Model.Liste;
import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.Service.Interface.ServiceInterface;

public class MessagingService implements ServiceInterface {

  public void getAllUsers(Request req, Response res) throws IOException{
    res.setStatus(Type.OK);
    List<String> list = new ArrayList<String>();
    for(AsynchronousSocketChannel cli : App.clients){
      list.add(cli.getRemoteAddress().toString());
    }
    Liste l = new Liste(list);
    res.setObj(l);
    res.setSendToAll(true);
    res.setType(Type.GET_USERS);
  }
  public void echoMessage(Request req, Response res) throws IOException{
    res.setStatus(Type.OK);   
    String resp = "Le server a bien reçu : " + req.getParams().get("message");
    res.setObj(resp);
    res.setType(Type.ANY);   
  }
  @Override
  public void run(Request req, Response res, AsynchronousSocketChannel client) 
    throws IOException {
    Type type = req.getType();
    if(type.equals(Type.GET_USERS)){
      System.out.println("On va filer à tout le monde la liste des clients !");
      getAllUsers(req, res);
    }
    if(type.equals(Type.ANY)){
      System.out.println("On va lui renvoyer son message !");
      echoMessage(req, res);
    }
  }
}
