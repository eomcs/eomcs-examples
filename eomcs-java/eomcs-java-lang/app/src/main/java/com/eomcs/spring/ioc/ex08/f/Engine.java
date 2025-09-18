package com.eomcs.spring.ioc.ex08.f;

public class Engine {
  String maker;
  int valve;
  int cylinder;

  public Engine() {
    System.out.println("Engine()");
  }

  @Override
  public String toString() {
    return "Engine [maker=" + maker + ", valve=" + valve + ", cylinder=" + cylinder + "]";
  }
  public String getMaker() {
    return maker;
  }
  public void setMaker(String maker) {
    this.maker = maker;
  }
  public int getValve() {
    return valve;
  }
  public void setValve(int valve) {
    this.valve = valve;
  }
  public int getCylinder() {
    return cylinder;
  }
  public void setCylinder(int cylinder) {
    this.cylinder = cylinder;
  }


}
