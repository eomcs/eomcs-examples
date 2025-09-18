package com.eomcs.design_pattern.observer.after.f;

public class Test01 {

  public static void main(String[] args) {
    Car car = new Car();

    car.addCarObserver(new SafeBeltCarObserver());
    car.addCarObserver(new EngineOilCarObserver());
    car.addCarObserver(new BrakeOilCarObserver());
    car.addCarObserver(new LightOffCarObserver());

    // 썬루프를 닫는 옵저버를 추가한다.
    // - 기존 구조에서는 Car 클래스에 코드를 추가하였다!!!
    // - 옵저버 패턴으로 구조를 바꾼 후에는 이렇게 새 클래스를 정의하여 등록한다.
    car.addCarObserver(new SunRoofCloseCarObserver());

    car.start();

    car.run();

    car.stop();

    // 업그레이드를 수행한 다음 시간이 지난 후
    // 5) 시동 끌 때 썬루프를 자동으로 닫기
    // => 썬루프 자동으로 닫는 옵저버(SunRoofCloseCarObserver)를 정의한다.
    // => Car 객체에 등록한다.
    //
  }

}


