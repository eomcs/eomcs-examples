package com.eomcs.spring.ioc.ex08.h;

import java.sql.Date;
import javax.annotation.Resource;

// 의존 객체 Engine 주입 - @Autowired + @Qualifier = @Resource
public class Car {

  private String model;
  private String maker;
  private int cc;
  private boolean auto;
  private Date createdDate;

  // => 이 애노테이션은 스프링 프레임워크가 아닌 자바에서 제공한다.
  // => 자바 기본 라이브러리에 포함되어 있지 않다.
  // => 따로 라이브러리를 다운로드 받아 포함시켜야 한다.
  // => search.maven.org 에서 'javax.annotation'으로 검색하라.
  //    1.3.2 이상의 버전 사용.
  @Resource(name = "e1")
  //  @Autowired
  //  @Qualifier("e1")
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

  // public void setEngine(Engine engine) {
  // System.out.println("Car.setEngine()");
  // this.engine = engine;
  // }

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
