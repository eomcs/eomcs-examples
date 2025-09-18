package com.eomcs.design_pattern.observer.after.a;

import java.util.ArrayList;
import java.util.List;

public class Car {

  //-------------------------------------------------------------------
  // Observer 디자인 패턴 적용:
  // - publisher 쪽에 추가해야 하는 필드와 메서드 
  // 관찰자(observer/listener/subscriber)의 객체 주소를 보관한다.
  List<CarObserver> observers = new ArrayList<>();

  // 자동차의 상태 변경을 보고 받을 관찰자(Observer)를 등록한다.
  public void addCarObserver(CarObserver observer) {
    observers.add(observer);
  }

  // 자동차의 상태 변경을 보고 받는 관찰자를 제거한다.
  public void removeCarObserver(CarObserver observer) {
    observers.remove(observer);
  }
  //-------------------------------------------------------------------

  public void start() {
    System.out.println("시동을 건다.");

    // ---------------------------------------------------
    // Observer 디자인 패턴:
    // - publisher의 상태가 바뀌었을 때 subscriber에게 통지한다.
    // - 즉 subscriber(observer/listener)에 대해 규칙(CarObserver 인터페이스)에 따라 메서드를 호출한다.
    // 예) 자동차의 시동을 걸면, 등록된 관찰자들에게 알린다.
    for (CarObserver observer : observers) {
      observer.carStarted();
    }
    // ---------------------------------------------------
  }

  public void run() {
    System.out.println("달린다.");
  }

  public void stop() {
    System.out.println("시동을 끈다.");

    // ---------------------------------------------------
    // Observer 디자인 패턴:
    // - publisher의 상태가 바뀌었을 때 subscriber에게 통지한다.
    // - 즉 subscriber(observer/listener)에 대해 규칙(CarObserver 인터페이스)에 따라 메서드를 호출한다.
    // 예) 자동차의 시동을 끄면, 등록된 관찰자들에게 보고한다.
    for (CarObserver observer : observers) {
      observer.carStopped();
    }
    // ---------------------------------------------------
  }
}


