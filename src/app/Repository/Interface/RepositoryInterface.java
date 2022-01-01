package app.Repository.Interface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import app.utils.DBCredentials;

public abstract class RepositoryInterface {
  protected Connection con;

  protected RepositoryInterface() throws ClassNotFoundException, SQLException{
    try{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Map<String,String> credentials = DBCredentials.getAccessToken();
      con = DriverManager.getConnection(credentials.get("url"),credentials.get("username"), credentials.get("password"));
    } catch (SQLException e) {
      throw e;
    }
  }
}
