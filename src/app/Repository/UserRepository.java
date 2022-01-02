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
  
  public UserRepository(Connection con) throws ClassNotFoundException, SQLException {
    super(con);
  }
  public User findByUsernameAndPassword(String username, String password) throws SQLException, NoSuchAlgorithmException {
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("select * from User where username='"+ username + "' and password='"+ PasswordEncoder.encode(password) +"'");
  
      if(!res.next()){
        return null;
      }
      User user = new User();
      user.setUsername(res.getString("username"));
      user.setUuid(res.getInt("uuid"));
      return user;
    } catch(SQLException e){
      throw e;
    } catch (NoSuchAlgorithmException e){
      throw e;
    }
  }
  public User insert(String username, String password) throws SQLException, NoSuchAlgorithmException {
    try{
      Statement stmt = con.createStatement();
      ResultSet res = stmt.executeQuery("select * from User where username='"+ username+"'");
      if(res.next()){
        return null;
      }
      else{
        stmt.executeUpdate("INSERT INTO User (username,password) VALUES ('"+username+"','"+PasswordEncoder.encode(password)+"')");
        res = stmt.executeQuery("Select max(uuid) from User");
        User user = new User();
        if(res.next()){
          user.setUuid(res.getInt("max(uuid)"));
          user.setUsername(username);
        }
        return user;
      }
    }
    catch(SQLException e){
      throw e;
    }
    catch(NoSuchAlgorithmException e ){
      throw e;
    }
  }
}
