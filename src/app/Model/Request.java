package app.Model;

import java.io.Serializable;
import java.util.Map;

public class Request implements Serializable {
  private Type type;
  private Map<String, String> params;

  public Request() {
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public void setParams(Map<String, String> params) {
    this.params = params;
  }

  
}
