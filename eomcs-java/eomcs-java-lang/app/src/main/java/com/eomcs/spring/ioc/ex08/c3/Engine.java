package com.eomcs.spring.ioc.ex08.c3;

public class Engine {
  String maker;
  int valve;
  int cylinder;

  public Engine() {
    System.out.println("Engine()");
  }

  public void init() {
    System.out.println("Engine().init()");
  }

  @Override
  public String toString() {
    return "Engine [maker=" + maker + ", valve=" + valve + ", cylinder=" + cylinder + "]";
  }

  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    System.out.println("Engine.setMaker()");
    this.maker = maker;
  }

  public int getValve() {
    return valve;
  }

  public void setValve(int valve) {
    System.out.println("Engine.setValve()");
    this.valve = valve;
  }

  public int getCylinder() {
    return cylinder;
  }

  public void setCylinder(int cylinder) {
    System.out.println("Engine.setCylinder()");
    this.cylinder = cylinder;
  }


}
