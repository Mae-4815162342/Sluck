package app.Model;
import app.Repo.UserRepository;
public class User {
    private final int id;
    private String pseudo;
    private String password;

    public User(String pseudo,String password){
        this.pseudo = pseudo;
        this.password = password;
        System.out.println(UserRepository.getNbUsers());
        this.id = UserRepository.getNbUsers() +1 ;
    }
    public User(int id, String pseudo,String password){
        this.pseudo = pseudo;
        this.password = password;
        this.id = id;
    }

    public String getPseudo(){return pseudo;}

    public int getId(){return id;}

    public String getPassword(){return password;}
}
