package app.Model;

import java.io.Serializable;

public class Response implements Serializable {
  private int ID;
  private Object obj;

  public Response(int iD, Object obj) {
    ID = iD;
    this.obj = obj;
  }

  public int getID() {
    return ID;
  }

  public void setID(int iD) {
    ID = iD;
  }

  public Object getObj() {
    return obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }
  
}
