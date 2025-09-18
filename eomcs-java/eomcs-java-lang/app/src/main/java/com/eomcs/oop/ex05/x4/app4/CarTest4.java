package com.eomcs.oop.ex05.x4.app4;

// 하이브리드 자동차 만들기
//
// 3) 기존 코드를 상속하여 새 클래스를 만든 후 기능 덧붙이기
//
public class CarTest4 {

  public static void main(String[] args) {
    // Car 클래스를 상속 받아 HybridCar 클래스를 만든 후 전기차 기능을 추가한다.
    // => kwh 변수 추가
    // => chargeBattery() 메서드 추가
    // => run() 메서드 오버라이딩
    //
    HybridCar car = new HybridCar();
    //    car.chargeBattery(100);
    car.start();
    car.run();
    car.stop();

  }

}
