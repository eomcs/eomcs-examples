package com.eomcs.spring.ioc.ex01;

import org.springframework.stereotype.Component;

@Component
public class Car {
  String model;
  String maker;
  int cc;

  public Car() {
    System.out.println("Car() 생성자 호출!");
  }

  @Override
  public String toString() {
    return "Car [model=" + model + ", maker=" + maker + ", cc=" + cc + "]";
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getMaker() {
    return maker;
  }

  public void setMaker(String maker) {
    this.maker = maker;
  }

  public int getCc() {
    return cc;
  }

  public void setCc(int cc) {
    this.cc = cc;
  }


}
