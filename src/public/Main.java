import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;
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
