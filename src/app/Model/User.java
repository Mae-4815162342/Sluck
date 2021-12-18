package app.Model;

public class User {
    private String pseudo;
    private String password;

    public User(String pseudo,String password){
        this.pseudo = pseudo;
        this.password = password;
    }

    public String getPseudo(){return pseudo;}
}
