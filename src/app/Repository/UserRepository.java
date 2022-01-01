package app.Repository;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.Model.User;
import app.Repository.Interface.RepositoryInterface;
import app.utils.PasswordEncoder;

public class UserRepository extends RepositoryInterface {
  
  private static Connection con;

  public UserRepository() throws ClassNotFoundException, SQLException {
    super();
  }
  public User findByUsernameAndPassword(String username, String password) throws SQLException, NoSuchAlgorithmException, Exception{
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("select * from User where username='"+ username + "' and password='"+ PasswordEncoder.encode(password) +"'");
  
      if(res== null){
        throw new Exception("Could not login into account");
      }
  
      User user = new User();
      user.setPassword(res.getString("password"));
      user.setUsername(res.getString("username"));
      user.setUuid(res.getInt("uuid"));
      return user;
    } catch(SQLException e){
      throw e;
    } catch (NoSuchAlgorithmException e){
      throw e;
    }
  }
  public User findById(int id) throws SQLException, NoSuchAlgorithmException, Exception{
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("select * from User where uuid='"+ id + "'");
  
      if(res== null){
        throw new Exception("Could not login into account");
      }
  
      User user = new User();
      user.setPassword(res.getString("password"));
      user.setUsername(res.getString("username"));
      user.setUuid(res.getInt("uuid"));
      return user;
    } catch(SQLException e){
      throw e;
    } catch (NoSuchAlgorithmException e){
      throw e;
    }
  }

  public User insert(User user) throws NoSuchAlgorithmException, SQLException {
    try{
      Statement stmt = con.createStatement();
      stmt.executeUpdate("INSERT INTO User VALUES ('"+user.getUuid()+"','"+user.getUsername()+"','"+PasswordEncoder.encode(user.getPassword())+"')");
  
      return user;
    }
    catch(SQLException e){
      throw e;
    }
    catch(NoSuchAlgorithmException e ){
      throw e;
    }
  }
}
