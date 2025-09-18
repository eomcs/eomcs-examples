// 상속 - 추상 메서드
package com.eomcs.oop.ex05.n;

// Car 클래스의 start()와 shutdown()은 서브 클래스에서 그대로 받아 사용해도 된다.
// 그러나 run() 메서드는 서브 클래스마다 자신의 특징에 맞춰 재정의해야 한다.
// 그렇다면 굳이 수퍼 클래스에서 run() 메서드를 구현할 필요가 없지 않는가?
// run()처럼 서브 클래스에서 무조건 재정의되어야 하는 메서드인 경우
// 즉 수퍼 클래스에서 정의하지 않고 서브클래스에 반드시 정의하도록 강제하는 문법이 
// "추상메서드(abstract method)"이다.
//
public class Exam01 {

  public static void main(String[] args) {
    Sedan car1 = new Sedan();
    car1.run();
  }

}





