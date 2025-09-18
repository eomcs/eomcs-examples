package com.eomcs.oop.ex05.x4.app3;

// 트레일러 만들기
//
// 3) 기존 코드를 상속하여 새 클래스를 만든 후 기능 덧붙이기
//
public class CarTest3 {

  public static void main(String[] args) {
    // 트레일러 클래스 추가
    // => CampingTrailer 클래스 정의
    //
    // Car 클래스를 상속 받아 TrailerCar 클래스를 만든 후 트레일러 연결 기능을 추가한다.
    // => trailer 변수 추가
    // => setTrailer() 메서드 추가
    // => run() 메서드 오버라이딩

    CampingTrailer trailer = new CampingTrailer();

    TrailerCar car = new TrailerCar();
    //car.setTrailer(trailer);
    car.start();
    car.run();
    car.stop();
  }

}
