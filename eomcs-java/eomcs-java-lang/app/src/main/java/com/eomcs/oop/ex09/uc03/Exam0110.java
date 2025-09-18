// 인터페이스 활용: 스태틱 메서드의 활용
package com.eomcs.oop.ex09.uc03;

public class Exam0110 {
  public static void main(String[] args) {

    Tico c1 = new Tico(); // Car 객체이면서 CarCheckInfo 로 사용할 수 있는 객체이다.
    //    c1.setGas(90);
    //    c1.setEngineOil(80);
    //    c1.setBrakeOil(100);

    // Tico가 직접 CarCheckInfo 를 구현한 것은 아니지만,
    // Tico의 수퍼 클래스가 구현했기 때문에
    // Tico도 구현한 것으로 간주한다.
    // 왜?
    // - Tico 클래스는 수퍼 클래스가 구현한 메서드를 상속 받기 때문이다.
    //
    if (CarCheckInfo.validate(c1)) {
      c1.start();
      c1.run();
      c1.shutdown();
    } else {
      System.out.println("자동차를 점검하시기 바랍니다. 문제 발생!");
    }

  }
}
