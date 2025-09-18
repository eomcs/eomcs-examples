package com.eomcs.oop.ex05.x2;

// 하이브리드 자동차 만들기
//
// 1) 기존 코드에 기능 덧붙이기
//
public class CarTest4 {

  public static void main(String[] args) {

    Engine car = new Engine();
    car.chargeBattery(100);
    car.start();
    car.run();
    car.stop();

  }

}
