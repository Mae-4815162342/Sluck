import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
    try(AsynchronousSocketChannel socket = AsynchronousSocketChannel.open()) {
      socket.connect(new InetSocketAddress("localhost", 1237)).get();
      Scanner scanner = new Scanner(System.in);
      String response = "";
      while (!response.equals("Bye !")) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("Nouvel envoi");
        String line = scanner.nextLine();
        buffer.put(line.getBytes("UTF-8")).flip();
        System.out.println(line);
        socket.write(buffer).get();

        buffer.clear();
        socket.read(buffer).get();
        response = new String(buffer.flip().array()).trim();
        System.out.println("J'ai reçu " + response);
      }
      scanner.close();
    }
	}

}
