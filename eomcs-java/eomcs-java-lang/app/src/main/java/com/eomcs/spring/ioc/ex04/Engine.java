package com.eomcs.spring.ioc.ex04;

public class Engine {
  String maker;
  int valve;
  int cylinder;

  public Engine() {
    System.out.println("Engine() 생성자 호출됨!");
  }

  @Override
  public String toString() {
    return "Engine [maker=" + maker + ", valve=" + valve + ", cylinder=" + cylinder + "]";
  }
  public String getMaker() {
    return maker;
  }
  public void setMaker(String maker) {
    System.out.println("Engine.setMaker() 호출됨!");
    this.maker = maker;
  }
  public int getValve() {
    return valve;
  }
  public void setValve(int valve) {
    System.out.println("Engine.setValve() 호출됨!");
    this.valve = valve;
  }
  public int getCylinder() {
    return cylinder;
  }
  public void setCylinder(int cylinder) {
    System.out.println("Engine.setCylinder() 호출됨!");
    this.cylinder = cylinder;
  }


}
