package app.Repo;

import java.sql.*;

import app.Model.*;

public class UserRepository {

    private static Connection con = null;

    public static Connection connexion() throws Exception {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = null;
        //pscale_pw_dqyb4d5E_Eb0gdSxd4mwOH3LZekskdPIcVC5fylFNMc
        //main-2022-jan-3-yg8awu
        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://16ekkl1kiyxk.eu-west-2.psdb.cloud/sluckdb?sslMode=VERIFY_IDENTITY",
                "8zeoxqw0gspt", "pscale_pw_dqyb4d5E_Eb0gdSxd4mwOH3LZekskdPIcVC5fylFNMc");
            System.out.println("Connexion avec la base de donnees etablie");
        } catch (SQLException e) {
            throw e;
        }
        return con;
    }

    public static void setConnexion(Connection c){
        con = c;
    }
  
	public static int getNbUsers(){
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            ResultSet usersRes = stmt.executeQuery("SELECT COUNT(*) FROM User");
            usersRes.next();
            return usersRes.getInt("COUNT(*)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return -1;
	}

	public static void addUser(User user){
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate("INSERT INTO User Values("+user.getId()+",'"+user.getPseudo()+"','"+user.getPassword()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(User user){
        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM User WHERE uuid = "+user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUserById(int id){
        User user = null;

        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select * from User where User.uuid = "+ id);
            while (res.next()) {
                user= new User(res.getInt("uuid"),res.getString("username"),res.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User getUserByName(String name){
        User user = null;

        try {
            if (con == null) {
                try {
                    con = connexion();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery("select * from User where User.username = '"+ name+"'");
            
            return new User(res.getInt("uuid"),res.getString("username"),res.getString("password"));
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
