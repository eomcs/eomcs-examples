package com.eomcs.oop.ex05.x4.app2;

// 트럭 만들기
//
// 3) 기존 코드를 상속하여 새 클래스를 만든 후 기능 덧붙이기
//
public class CarTest2 {

  public static void main(String[] args) {
    // Car 클래스를 상속 받아 Truck 클래스를 만든 후 트럭을 위한 덤프 기능을 추가한다.
    // => dump() 메서드 추가

    Truck car = new Truck();
    car.start();
    car.run();
    car.dump();
    car.stop();

  }

}
