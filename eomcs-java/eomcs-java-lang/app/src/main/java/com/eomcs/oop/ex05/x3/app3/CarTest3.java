package com.eomcs.oop.ex05.x3.app3;

// 트레일러 만들기
//
// 2) app1에서 복제해 온 Engine에 기능 덧붙이기
//
public class CarTest3 {

  public static void main(String[] args) {
    // 트레일러 클래스 추가
    // => CampingTrailer 클래스 정의
    //
    // Engine 클래스에 트레일러 연결 기능을 추가한다.
    // => trailer 변수 추가
    // => setTrailer() 메서드 추가
    // => run() 메서드 변경

    CampingTrailer trailer = new CampingTrailer();

    Engine car = new Engine();
    //car.setTrailer(trailer);
    car.start();
    car.run();
    car.stop();
  }

}
