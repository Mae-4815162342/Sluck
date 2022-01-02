package app.Model;

import java.io.Serializable;
import java.util.List;

public class Liste implements Serializable {
  private List<String> list;

  public Liste(List<String> list){
    this.list = list;
  }
  public String toString(){
    String res = "";
    for(int i = 0; i<list.size(); i++){
      res += i+": " + list.get(i) + System.getProperty("line.separator");
    }
    return res;
  }
}
