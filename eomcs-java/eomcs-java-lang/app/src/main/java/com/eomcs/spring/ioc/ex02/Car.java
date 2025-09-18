package com.eomcs.spring.ioc.ex02;

public class Car {
  String model;
  String maker;
  int cc;

  public Car() {
    System.out.println("Car() 생성자 호출됨!");
  }

  @Override
  public String toString() {
    return "Car [model=" + model + ", maker=" + maker + ", cc=" + cc + "]";
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
