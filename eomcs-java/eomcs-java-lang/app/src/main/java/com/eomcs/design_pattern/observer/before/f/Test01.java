package com.eomcs.design_pattern.observer.before.f;

public class Test01 {

  public static void main(String[] args) {
    Car car = new Car();

    car.start();

    car.run();

    car.stop();

    // 업그레이드를 수행한 다음 시간이 지난 후
    // 5) 시동 끌 때 썬루프를 자동으로 닫기
    // => Car의 stop() 메서드에 해당 코드 추가

    // 결론!
    // => 기존의 프로그래밍 방식은 특정 상태에서 수행하는 기능을 추가할 때
    //    기존 클래스에 계속 코드를 추가해야 했다.
    // => 기존 코드에 계속 새 코드를 추가하는 방식은 유지보수에 좋지 않다.
    // => Observer 패턴을 적용하면 기존 클래스를 손대지 않고
    //    특정 상태에서 수행하는 작업을 쉽게 추가할 수 있다.
  }

}


