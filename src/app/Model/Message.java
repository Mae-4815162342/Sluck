package app.Model;

import java.io.Serializable;

public class Message implements Serializable{
    private int muid;
    private String message;
    private String username;
    private int cuid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Message(){
    }

    public int getMuid() {
        return muid;
    }

    public void setMuid(int muid) {
        this.muid = muid;
    }

    public String getMessage() {
        return message;
    }

    public int getCuid() {
        return cuid;
    }

    public void setCuid(int cuid) {
        this.cuid = cuid;
    }

    //pour modifier un message
    public void setMessage(String message){
        this.message = message;
    }
    public String toString(){
        return "From : "+ username + " in " + cuid + ", " + message;
    }
}
