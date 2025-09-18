package com.eomcs.design_pattern.observer.after.g;

// Car 클래스를 리팩토링 한다.
// - 옵저버에 통지하는 코드를 별도의 메서드로 분리하여
// 유지보수 하기 좋게 만든다.
public class Test01 {

  public static void main(String[] args) {
    Car car = new Car();

    car.addCarObserver(new SafeBeltCarObserver());
    car.addCarObserver(new EngineOilCarObserver());
    car.addCarObserver(new BrakeOilCarObserver());
    car.addCarObserver(new LightOffCarObserver());
    car.addCarObserver(new SunRoofCloseCarObserver());

    car.start();

    car.run();

    car.stop();
  }

}


