package app.Service.Interface;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.sql.SQLException;

import app.Model.Request;
import app.Model.Response;

public interface ServiceInterface {
  public void run(Request req, Response res, AsynchronousSocketChannel client) throws IOException, SQLException;
}
