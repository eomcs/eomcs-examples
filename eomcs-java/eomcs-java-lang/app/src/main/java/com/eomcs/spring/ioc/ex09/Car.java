package com.eomcs.spring.ioc.ex09;

import java.sql.Date;
import org.springframework.stereotype.Component;

// @Component
// => 스프링 IoC 컨테이너는 이 애노테이션이 붙은 클래스에 대해 객체를 자동 생성한다.
// 문법:
//      @Component(value="객체이름")
//      @Component("객체이름")
// 만약 다음과 같이 객체의 이름을 생략하면
// 클래스 이름을 객체 이름으로 사용한다.
//      예) bitcamp.java106.step09.Car => "car"
// 즉 클래스 이름에서 첫 알파벳을 소문자로 한 이름을
// 객체 이름으로 사용한다.
//
@Component
public class Car {
  String model;
  String maker;
  int cc;
  boolean auto;
  Date createdDate;
  Engine engine;

  // 의존 객체는 생성자에서 주입하는 것이 좋다.
  // 의존 객체라는 말에서 이미 그 객체없이는 작업을 수행할 수 없다는 의미이기 때문에
  // 보통 필수 객체이다.
  // 따라서 생성자에서 필수 객체를 받게 하는 것이 유지보수에 좋다.
  // 즉 의존 객체없이 해당 객체를 생성하는 일을 방지할 수 있기 때문이다.
  public Car(Engine engine) {
    this.engine = engine;
  }

  @Override
  public String toString() {
    return "Car [model=" + model + ", maker=" + maker + ", cc=" + cc + ", auto=" + auto + ", createdDate="
        + createdDate + ", engine=" + engine + "]";
  }
  /*
   * 의존 객체는 작업하는데 사용하라고 생성자를 호출할 때 외부에서 주입하는 객체이기 때문에
   * 셋터나 겟터를 정의할 필요가 없다.
    public Engine getEngine() {
        return engine;
    }
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
   */
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
