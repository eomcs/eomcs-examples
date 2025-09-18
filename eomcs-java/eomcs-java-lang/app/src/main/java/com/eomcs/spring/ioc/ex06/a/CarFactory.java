package com.eomcs.spring.ioc.ex06.a;

import com.eomcs.spring.ioc.ex06.Car;

public class CarFactory {

  public CarFactory() {
    System.out.println("CarFactory() 생성자 호출!");
  }

  public static Car create(String model) {
    System.out.printf("CarFactory.create(%s) 호출됨\n", model);

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
