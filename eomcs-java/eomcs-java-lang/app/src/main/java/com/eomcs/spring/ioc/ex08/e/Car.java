package com.eomcs.spring.ioc.ex08.e;

import java.sql.Date;

// 의존 객체 Engine 주입 - 생성자를 통해 의존 객체 주입
public class Car {

  private String model;
  private String maker;
  private int cc;
  private boolean auto;
  private Date createdDate;

  //@Autowired
  private Engine engine;

  // Spring IoC 컨테이너로부터 의존 객체를 주입 받고 싶다면,
  // 생성자에 파라미터를 선언하라.
  // @Autowired 는 붙일 필요가 없다. (붙여도 된다)
  // 주의!
  // => 이 일 또한 AutowiredAnnotationBeanPostProcessor 객체가 처리한다.
  //
  public Car(Engine engine) {
    System.out.println("Car(Engine)");
    this.engine = engine;
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
