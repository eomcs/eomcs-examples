package com.eomcs.spring.ioc.ex08.d;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;

// 의존 객체 Engine 주입 - 필드에 @Autowired로 표시하라!
public class Car {

  private String model;
  private String maker;
  private int cc;
  private boolean auto;
  private Date createdDate;

  // 필드에 @Autowired를 붙인 경우,
  // 셋터를 통해 값을 넣는 것이 아니라,
  // 인스턴스 필드에 직접 값을 넣는다.
  // private 이라도 상관없다.
  // 따라서 셋터를 정의하지 않아도 된다.
  @Autowired 
  private Engine engine;

  public Car() {
    System.out.println("Car()");
  }


  @Override
  public String toString() {
    return "Car [model=" + model + ", maker=" + maker + ", cc=" + cc + ", auto=" + auto
        + ", createdDate=" + createdDate + ", engine=" + engine + "]";
  }

  public Engine getEngine() {
    return engine;
  }

  //  @Autowired
  //  public void setEngine(Engine engine) {
  //    System.out.println("Car.setEngine()");
  //    this.engine = engine;
  //  }

  public boolean isAuto() {
    return auto;
  }

  public void setAuto(boolean auto) {
    this.auto = auto;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
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
