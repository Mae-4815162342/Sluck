package app;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import app.Model.Liste;
import app.Model.Response;

public class Main {

  public static Response deserializeResponse(byte[] bytes) throws ClassNotFoundException, IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Response l = (Response) in.readObject(); 
			return l;
		}  
		finally{
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw e;
			}
		} 
	}

  public static Liste deserializeListe(byte[] bytes) throws ClassNotFoundException, IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Liste l = (Liste) in.readObject(); 
			return l;
		} 
    catch(ClassNotFoundException e){
      throw e;
    } 
		finally{
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw e;
			}
		} 
	}
	public static void main(String[] args) throws Exception {
    try(AsynchronousSocketChannel socket = AsynchronousSocketChannel.open()) {
      socket.connect(new InetSocketAddress("localhost", 1237)).get();
      Scanner scanner = new Scanner(System.in);
      ExecutorService pool = Executors.newCachedThreadPool();
      String line = "";
      pool.submit(() -> {
        while(true){
          ByteBuffer buffer = ByteBuffer.allocate(1024);
          socket.read(buffer).get();
          buffer.flip();
          Response resp = deserializeResponse(buffer.array());
          if(resp.getID()==1){
            Liste l = (Liste) resp.getObj();
            System.out.print(l.toString());
          }
          else{
            System.out.println("J'ai reçu " + (String) resp.getObj()); 
          }
          /* try{
            Liste l = deserializeListe(buffer.array());
            System.out.print(l.toString());
          }
          catch(ClassNotFoundException e){
            String response = new String(buffer.array()).trim();
            System.out.println("J'ai reçu " + response); 
          }
          catch(IOException e){
            
          } */
        }
      });
      Runtime.getRuntime().addShutdownHook(new Thread() {
        public void run() {
            try {
                Thread.sleep(200);
                socket.write(ByteBuffer.wrap("exit".getBytes())).get();
                //some cleaning up code...
  
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } catch (ExecutionException e) {
              e.printStackTrace();
            }
        }
      });
      while(!line.equals("Bye !")){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        line = scanner.nextLine();
        buffer.put(line.getBytes("UTF-8")).flip();
        socket.write(buffer).get();
      }
      scanner.close();
    }
	}
}
