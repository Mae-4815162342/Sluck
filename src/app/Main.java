package app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.Model.Liste;
import app.Model.Request;
import app.Model.Response;
import app.Model.Type;
import app.utils.SerializationUtils;

public class Main {

  private volatile String line; 
  public Main(String line) {
    this.line = line;
  }
  public void waitForResponse(AsynchronousSocketChannel socket, String line) throws 
    InterruptedException, ExecutionException, ClassNotFoundException, IOException{
    while(true){
      ByteBuffer buffer = ByteBuffer.allocate(1024);
      socket.read(buffer).get();
      handleResponse(buffer);
    }
  }
  public Request outputParser(String line){
    Request req = new Request();
    Map<String, String> params = new HashMap<String, String>();
    switch(line){
      case "liste":
        req.setType(Type.GET_USERS);
      default:
        req.setType(Type.ANY);
        params.put("message", line);
        req.setParams(params);
    }
    return req;
  }
  public void handleRequest(AsynchronousSocketChannel socket, String line, Scanner scanner) throws 
    InterruptedException, ExecutionException, IOException, ClassNotFoundException{
      while(!line.equals("Bye !")){
        line = scanner.nextLine();
        Request req = outputParser(line);
        Request reqBis = SerializationUtils.deserializeRequest(SerializationUtils.serializeRequest(req));
        System.out.println("Force Reading : " + req.getParams().get("message"));
        System.out.println("Force Reading : " + (String) reqBis.getParams().get("message"));
        socket.write(ByteBuffer.wrap(SerializationUtils.serializeRequest(req))).get();
      }
    sendDisconnectRequest(socket);
    scanner.close();
  }
  public void handleResponse(ByteBuffer buffer) throws 
    ClassNotFoundException, IOException{
      Response response = SerializationUtils.deserializeResponse(buffer.flip().array());
      if(response.getStatus().equals(Type.OK)){
        switch(response.getType()){
          case ANY:
            String echoMessage = (String) response.getObj();
            System.out.print(echoMessage);
            break;
          case CREATE_CHANNEL:
            break;
          case ERROR:
            break;
          case EXIT:
            break;
          case GET_CHANNELS:
            break;
          case GET_USERS:
            Liste l = (Liste) response.getObj();
            System.out.print(l.toString());
          case OK:
            break;
          case SEND_MESSAGES:
            break;
          case SIGNIN:
            break;
          case SIGNOUT:
            break;
          case SIGNUP:
            break;
          default:
            break; 
        }
      }
  }
  public void sendDisconnectRequest(AsynchronousSocketChannel socket) throws 
    InterruptedException, ExecutionException, IOException{
      Request req = new Request();
      req.setType(Type.SIGNOUT);
      req.setParams(null);
      socket.write(ByteBuffer.wrap(SerializationUtils.serializeRequest(req))).get();
  }
  public void setShutdownHook(AsynchronousSocketChannel socket){
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
          try {
            Thread.sleep(200);
            sendDisconnectRequest(socket);
          } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              e.printStackTrace();
          } catch (ExecutionException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
      }
    });
  }
  public void run() throws 
    InterruptedException, ExecutionException, ClassNotFoundException, IOException {
      try(AsynchronousSocketChannel socket = AsynchronousSocketChannel.open()) {
      socket.connect(new InetSocketAddress("localhost", 1237)).get();
      Scanner scanner = new Scanner(System.in);
      ExecutorService pool = Executors.newCachedThreadPool();
      pool.submit(() -> {
        try {
          waitForResponse(socket, line);
        } catch (ClassNotFoundException | InterruptedException | ExecutionException | IOException e) {
          e.printStackTrace();
        }
      });
      setShutdownHook(socket);
      handleRequest(socket, line, scanner);
    }
	}
  public static void main(String[] args) throws ClassNotFoundException, InterruptedException, ExecutionException, IOException {
    new Main("").run();
  }
}
