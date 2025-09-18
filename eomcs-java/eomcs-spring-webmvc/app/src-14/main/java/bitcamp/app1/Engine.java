package bitcamp.app1;

public class Engine {
  protected String model;
  protected int cc;
  protected int valve;
  
  @Override
  public String toString() {
    return "Engine [model=" + model + ", cc=" + cc + ", valve=" + valve + "]";
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public int getCc() {
    return cc;
  }
  public void setCc(int cc) {
    this.cc = cc;
  }
  public int getValve() {
    return valve;
  }
  public void setValve(int valve) {
    this.valve = valve;
  }
  
  
}
