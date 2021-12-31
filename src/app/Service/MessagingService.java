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
      list.add(cli.getLocalAddress().toString());
    }
    Liste l = new Liste(list);
    res.setObj(l);
    res.setSendToAll(true);
    res.setType(Type.GET_USERS);
  }
  public void echoMessage(Request req, Response res) throws IOException{
    res.setStatus(Type.OK);   
    res.setObj("Le server a bien reçu : " + req.getParams().get("message"));
  }
  @Override
  public void run(Request req, Response res, AsynchronousSocketChannel client) 
    throws IOException {
    Type type = req.getType();
    System.out.println("Sheesh, début MessagingService");
    if(type.equals(Type.GET_USERS)){
      getAllUsers(req, res);
    }
    if(type.equals(Type.GET_USERS)){
      echoMessage(req, res);
    }
  }
}
