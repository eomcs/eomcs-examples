package com.eomcs.spring.ioc.ex06.c;

import com.eomcs.spring.ioc.ex06.Car;

public class CarFactory {

  // 팩토리 메서드가 인스턴스 메서드이다.
  // => 팩토리 메서드가 non-static 메서드이다.
  public Car create(String model) {
    Car c = new Car();
    switch (model) {
      case "티코":
        c.setMaker("대우자동차");
        c.setModel("Tico");
        c.setCc(890);
        return c;
      case "소나타":
        c.setMaker("현대자동차");
        c.setModel("Sonata");
        c.setCc(1980);
        return c;
      case "SM5":
        c.setMaker("르노삼성자동차");
        c.setModel("SM5");
        c.setCc(1990);
        return c;
      default:
        c.setMaker("비트자동차");
        c.setModel("자바휘웅");
        c.setCc(5000);
        return c;
    }
  }
}
