package com.eomcs.spring.ioc.ex08.a;

import java.sql.Date;

public class Car {
  String model;
  String maker;
  int cc;
  boolean auto;
  Date createdDate;
  Engine engine;

  public Car() {
    System.out.println("Car()");
  }


  @Override
  public String toString() {
    return "Car [model=" + model + ", maker=" + maker + ", cc=" + cc + ", auto=" + auto + ", createdDate="
        + createdDate + ", engine=" + engine + "]";
  }
  public Engine getEngine() {
    return engine;
  }
  public void setEngine(Engine engine) {
    System.out.println("Car.setEngine()");
    this.engine = engine;
  }
  public boolean isAuto() {
    return auto;
  }
  public void setAuto(boolean auto) {
    System.out.println("Car.setAuto()");
    this.auto = auto;
  }
  public Date getCreatedDate() {
    return createdDate;
  }
  public void setCreatedDate(Date createdDate) {
    System.out.println("Car.setCreatedDate()");
    this.createdDate = createdDate;
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    System.out.println("Car.setModel()");
    this.model = model;
  }
  public String getMaker() {
    return maker;
  }
  public void setMaker(String maker) {
    System.out.println("Car.setMaker()");
    this.maker = maker;
  }
  public int getCc() {
    return cc;
  }
  public void setCc(int cc) {
    System.out.println("Car.setCc()");
    this.cc = cc;
  }


}
