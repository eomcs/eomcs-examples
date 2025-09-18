// 상속 - 추상클래스를 파라미터로 사용하기
package com.eomcs.oop.ex05.m;

public class Exam02 {

  public static void main(String[] args) {
    m1(new Truck()); // OK!
    //    m1(new Sedan()); // Error

    m2(new Truck());
    m2(new Sedan());

    //    m2(new Car()); // Error
  }

  static void m1(Truck car) {
    // 파라미터가 Truck이면,
    // 이 메서드를 호출할 때 반드시 Truck의 인스턴스나
    // 또는 Truck 의 하위 클래스의 인스턴스를 넘기라는 뜻이다.
  }

  static void m2(Car car) {
    // 파라미터가 Car이다.
    // 이 메서드를 호출할 때 Car의 하위 클래스의 인스턴스를 넘기라는 뜻이다.
    // Car는 추상 클래스이기 때문에 인스턴스를 생성할 수 없다.
  }

}





