package com.eomcs.oop.ex05.x5;

public class ElectricEngine extends Option {

  public ElectricEngine(Car car) {
    super(car);
  }

  int kwh;

  public void chargeBattery(int kwh) {
    this.kwh = kwh;
  }

  @Override
  public void start() {
    car.start();
  }

  @Override
  public void stop() {
    car.stop();
  }

  @Override
  public void run() {
    if (kwh > 0) {
      System.out.println("전기로 간다!");
    } else {
      car.run(); // 옵션 자체는 실제 자동차가 아니다. 그래서 run()이 호출되면 실제 자동차 객체에게 위임한다.
    }

  }
}
