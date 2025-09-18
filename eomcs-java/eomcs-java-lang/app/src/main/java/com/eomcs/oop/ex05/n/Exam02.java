// 상속 - 추상 메서드
package com.eomcs.oop.ex05.n;

// 메서드 호출
// => 메서드를 호출할 때는 항상 레퍼런스가 실제 가리키는 인스턴스의 클래스에서부터 찾는다.
public class Exam02 {

  public static void main(String[] args) {
    Car car = new Sedan();
    car.run();
  }

}





