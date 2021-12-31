package app;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import app.Model.Request;
import app.Model.Response;
import app.Model.User;
import app.router.Router;
import app.utils.SerializationUtils;


public class App implements Callable<Boolean> {
  private AsynchronousServerSocketChannel server;
	public static List<AsynchronousSocketChannel> clients = new ArrayList<AsynchronousSocketChannel>();
	public static Map<AsynchronousSocketChannel, User> users = new HashMap<AsynchronousSocketChannel, User>();
	private Router router;
	public App() throws Exception{
			router = new Router();
	}
	public void waitForRequest(AsynchronousSocketChannel client) throws Exception{
		while(true){
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			client.read(buffer).get();
			Request req = SerializationUtils.deserializeRequest(buffer.flip().array());
			Response res = new Response();
			router.run(req, res, client);
			if(res.isSendToAll()){
				sendToAll(res);
			}
			else{
				sendToOneClient(res, client);
			}
			/* String response = new String(buffer.flip().array()).trim();

			if(response.equals("liste")){
				Liste liste = createListe(clients);
				//byte [] listeByte = serializeListe(liste);
				Response resp = new Response();
				byte [] listeByte = serializeResponse(resp);
				/* Liste listeBis = deserializeListe(listeByte);
				System.out.println(listeBis.toString());
				for(AsynchronousSocketChannel cli : clients){
					cli.write(ByteBuffer.wrap(listeByte)).get();
				}
			}
			else{
				if(response.equals("exit")){
					//client.write(ByteBuffer.wrap("Bye !".getBytes())).get();
					System.out.println("Un client s'est déconnecté depuis " + client.getRemoteAddress());
					clients.remove(client);
					break;
				}
				else{
					Response resp = new Response();
					byte [] listeByte = serializeResponse(resp);
					client.write(ByteBuffer.wrap(listeByte)).get();
				} 
			}	*/
		}
	}
	public void sendToAll(Response res) throws InterruptedException, ExecutionException, IOException{
		for(AsynchronousSocketChannel cli : clients){
			cli.write(ByteBuffer.wrap(SerializationUtils.serializeResponse(res))).get();
		}
	}
	public void sendToOneClient(Response res, AsynchronousSocketChannel client) throws IOException, InterruptedException, ExecutionException{
		client.write(ByteBuffer.wrap(SerializationUtils.serializeResponse(res))).get();
	}
	@Override
	public Boolean call() throws Exception {
		server = AsynchronousServerSocketChannel.open();
		server.bind(new InetSocketAddress("localhost", 1237));
		server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

			@Override
			public void completed(AsynchronousSocketChannel client, Object attachment) {
				server.accept(null, this);
				try{
					System.out.println("Un client s'est connecté depuis " + client.getRemoteAddress());
					clients.add(client);
				} catch (Exception e) {
					failed(e, null);
				}
				try {
					waitForRequest(client);
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
