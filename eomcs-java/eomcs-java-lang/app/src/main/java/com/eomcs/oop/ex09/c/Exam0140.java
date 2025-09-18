// 인터페이스 다중 상속 불가!
package com.eomcs.oop.ex09.c;


public class Exam0140 {

  interface ProtocolA {
    void rule0();
    void rule1();
  }

  interface ProtocolB {
    int rule0();
    void rule2();
  }

  // ProtoclA와 ProtocolB에 메서드 시그너처에서 이름, 파라미터는 같지만
  // 리턴 타입이 다르다면 다중 상속이 불가능하다.
  // - 어느 수퍼 인터페이스의 메서드를 상속 받느냐에 따라
  //   동작이 달라지기 때문이다.
  //
  //  interface ProtocolC extends ProtocolA, ProtocolB { // 컴파일 오류!
  //    void rule3();
  //  }

  public static void main(String[] args) {
  }
}







