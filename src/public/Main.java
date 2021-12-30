import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

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
          String response = new String(buffer.flip().array()).trim();
          System.out.println("J'ai reçu " + response);
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
        System.out.println("Nouvel envoi");
        line = scanner.nextLine();
        buffer.put(line.getBytes("UTF-8")).flip();
        System.out.println(line);
        socket.write(buffer).get();
      }
      scanner.close();
    }
	}

}
