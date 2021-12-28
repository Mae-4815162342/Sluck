package app.Model;

public class User {
    private String uuid = null;
    private String username;
    private String password;

    public User(){
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String $uuid) {
        this.uuid = $uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername(){return username;}
}
