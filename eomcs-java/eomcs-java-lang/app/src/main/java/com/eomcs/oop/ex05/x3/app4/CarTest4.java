package com.eomcs.oop.ex05.x3.app4;

// 하이브리드 자동차 만들기
//
// 2) app1의 Engine 클래스를 복제해 온 후 하이브리드 기능 덧붙이기
//
public class CarTest4 {

  public static void main(String[] args) {
    // Engine 클래스에 하이브리드 기능 추가하기
    // => kwh 변수 추가
    // => chargeBattery() 메서드 추가
    // => run() 메서드 변경
    //
    Engine car = new Engine();
    //car.chargeBattery(100);
    car.start();
    car.run();
    car.stop();

  }

}
