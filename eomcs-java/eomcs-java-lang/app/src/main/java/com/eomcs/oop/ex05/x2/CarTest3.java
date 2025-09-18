package com.eomcs.oop.ex05.x2;

// 캠핑카 만들기
//
// 1) 기존 코드에 기능 덧붙이기
//
public class CarTest3 {

  public static void main(String[] args) {
    // Engine 클래스에 시동걸고 끄는 기능을 추가한다.
    // => start(), stop() 메서드 추가
    // => run() 메서드 변경

    CampingTrailer trailer = new CampingTrailer();

    Engine car = new Engine();
    car.setTrailer(trailer);
    car.start();
    car.run();
    car.stop();

  }

}
