package app.Model;

import java.io.Serializable;

public class Message implements Serializable{
    private int muid;
    private String message;
    private int uuid;
    private int cuid;

    public Message(){

    }

    public int getUuid(){
        return uuid;
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

    public void setUuid(int uuid) {
        this.uuid = uuid;
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
        return "From : "+uuid + " in " + cuid + ", " + message;
    }
}
