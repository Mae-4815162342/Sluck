package app.Model;

import java.io.Serializable;

public class Response implements Serializable{
  private Type status;
  private Type type;
  private boolean sendToAll; 
  private Object obj;


  public Response() {
    sendToAll = false;
  }

  public Type getStatus() {
    return status;
  }

  public void setStatus(Type status) {
    this.status = status;
  }

  public boolean isSendToAll() {
    return sendToAll;
  }

  public void setSendToAll(boolean sendToAll) {
    this.sendToAll = sendToAll;
  }
  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Object getObj() {
    return obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }
}
