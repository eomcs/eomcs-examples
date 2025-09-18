// 상속 - 추상클래스
package com.eomcs.oop.ex05.m;

// Car 클래스는 Sedan과 Truck의 공통 기능을 관리하기 위해 만든 클래스이다.
// 즉 Car 클래스는 직접 인스턴스를 만들어 사용하기 위해서 정의한 클래스가 아니라,
// Sedan과 Truck에서 겹치는 코드를 공통 관리하기 위해 만든 클래스이다.
// 가능한 직접 Car 클래스를 사용하는 것을 피해야 한다.
public class Exam01 {

  public static void main(String[] args) {
    // => 다음과 같이 Car 클래스를 사용하는 것을 막기 위해 추상 클래스 문법이 등장하였다.
    // => 만약 추상 클래스의 인스턴스를 만들려고 한다면 컴파일 오류가 발생할 것이다.
    Car c = new Car(); // 컴파일 오류!

    // generalization 과정에서 정의한 수퍼 클래스에 대해
    // 직접 사용하지 못하도록 추상클래스로 선언한다.

    Sedan s = new Sedan();
    Truck t = new Truck();
  }

}





