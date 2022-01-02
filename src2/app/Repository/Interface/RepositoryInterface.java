package app.Repository.Interface;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class RepositoryInterface {
  protected Connection con;

  protected RepositoryInterface(Connection con) throws ClassNotFoundException, SQLException{
    this.con = con;
  }
}
