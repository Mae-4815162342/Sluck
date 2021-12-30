package app;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import app.Model.Liste;
import app.Model.Response;

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
					System.out.println("Un client s'est connecté depuis " + client.getRemoteAddress());
					clients.add(client);
				} catch (Exception e) {
					failed(e, null);
				}
				try {
					while(true){
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						client.read(buffer).get();
						String response = new String(buffer.flip().array()).trim();

						if(response.equals("liste")){
							Liste liste = createListe(clients);
							//byte [] listeByte = serializeListe(liste);
							Response resp = new Response(1, liste);
							byte [] listeByte = serializeResponse(resp);
							/* Liste listeBis = deserializeListe(listeByte);
							System.out.println(listeBis.toString()); */
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
								Response resp = new Response(0, response);
								byte [] listeByte = serializeResponse(resp);
								client.write(ByteBuffer.wrap(listeByte)).get();
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
	public Liste createListe(List<AsynchronousSocketChannel> list) throws IOException{
		try{
			List<String> users = new ArrayList<String>();
			for(int i = 0; i<list.size(); i++){
				users.add(list.get(i).getRemoteAddress().toString());
			}
			return new Liste(users);
		}
		catch(IOException e){
			throw e;
		}
	}
	public byte[] serializeResponse(Response response) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try{
			out = new ObjectOutputStream(bos);
			out.writeObject(response);
			out.flush();
			byte[] res = bos.toByteArray();
			System.out.println(res.length);
			return res;
		}
		catch(IOException e){throw e;}
	}
	public Response deserializeResponse(byte[] bytes) throws ClassNotFoundException, IOException{
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
	public byte[] serializeListe(Liste list) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try{
			out = new ObjectOutputStream(bos);
			out.writeObject(list);
			out.flush();
			byte[] res = bos.toByteArray();
			System.out.println(res.length);
			return res;
		}
		catch(IOException e){throw e;}
	}
	public Liste deserializeListe(byte[] bytes) throws ClassNotFoundException, IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Liste l = (Liste) in.readObject(); 
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
	public static void main(String[] args) throws Exception {
		new App().call();
	}
}
