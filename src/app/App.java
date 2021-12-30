package app;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class App implements Callable<Boolean> {
  private AsynchronousServerSocketChannel server;
	private List<AsynchronousSocketChannel> clients = new ArrayList<AsynchronousSocketChannel>();

	@Override
	public Boolean call() throws Exception {
		server = AsynchronousServerSocketChannel.open();
		server.bind(new InetSocketAddress("localhost", 1237));
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

			@Override
			public void completed(AsynchronousSocketChannel client, Object attachment) {
				server.accept(null, this);
				try{
					System.out.println("A client is connected from " + client.getRemoteAddress());
					clients.add(client);
				} catch (Exception e) {
					failed(e, null);
				}
				try {
					while(true){
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						System.out.println("Nouvelle réception");
						client.read(buffer).get();
						System.out.println("Taille du buffer : " + buffer.position());
						String response = new String(buffer.flip().array()).trim();
						System.out.println("J'ai reçu " + response);

						if(response.equals("liste")){
							String res = "";
							for(int i = 0; i<clients.size(); i++){
								res += clients.get(i).getRemoteAddress() + ", ";
							}
							for(AsynchronousSocketChannel cli : clients){
								cli.write(ByteBuffer.wrap(res.getBytes())).get();
							}
						}
						else{
							if(response.equals("exit")){
								//client.write(ByteBuffer.wrap("Bye !".getBytes())).get();
								System.out.println("A client is disconnected from " + client.getRemoteAddress());
								clients.remove(client);
								break;
							}
							else{
								client.write(ByteBuffer.wrap(response.getBytes())).get();
							}
						}
					}
				} catch (Exception e) {
					failed(e, null);
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				exc.printStackTrace();
			}
		});
		System.in.read();
		return true;
	}

	public static void main(String[] args) throws Exception {
		new App().call();
	}
}
