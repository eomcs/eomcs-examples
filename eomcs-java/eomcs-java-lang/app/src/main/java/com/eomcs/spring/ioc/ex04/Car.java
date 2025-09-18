package com.eomcs.spring.ioc.ex04;

public class Car {
  String model;
  String maker;
  int cc;
  Engine engine;

  public Car() {
    System.out.println("Car() 생성자 호출됨!");
  }

  @Override
  public String toString() {
    return "Car [model=" + model + ", maker=" + maker + ", cc=" + cc + ", engine=" + engine + "]";
  }
  public Engine getEngine() {
    return engine;
  }
  public void setEngine(Engine engine) {
    System.out.println("setEngine() 호출됨!");
    this.engine = engine;
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    System.out.println("setModel() 호출됨!");
    this.model = model;
  }
  public String getMaker() {
    return maker;
  }
  public void setMaker(String maker) {
    System.out.println("setMaker() 호출됨!");
    this.maker = maker;
  }
  public int getCc() {
    return cc;
  }
  public void setCc(int cc) {
    System.out.println("setCc() 호출됨!");
    this.cc = cc;
  }


}
