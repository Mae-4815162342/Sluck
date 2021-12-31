package app.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import app.Model.Request;
import app.Model.Response;

public class SerializationUtils {
  public static byte[] serializeResponse(Response response) throws IOException{
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
  public static Request deserializeRequest(byte[] bytes) throws ClassNotFoundException, IOException{
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
    ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Request l = (Request) in.readObject(); 
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
}
