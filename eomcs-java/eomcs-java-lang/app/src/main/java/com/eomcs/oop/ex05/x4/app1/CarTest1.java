package com.eomcs.oop.ex05.x4.app1;

// 자동차를 만들기
//
// 3) 기존 코드를 상속하여 새 클래스를 만든 후 기능 덧붙이기
//
public class CarTest1 {

  public static void main(String[] args) {
    // Engine 클래스를 상속 받아 Car 클래스를 만든다.
    // => start(), stop() 메서드 추가
    Car car = new Car();
    car.start();
    car.run();
    car.stop();
  }
}
