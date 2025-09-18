// 캡슐화 접근 범위 테스트 - 종합

package com.eomcs.oop.ex08.b;

public class Exam0215 extends com.eomcs.oop.ex08.b.sub.Y {

  public static void main(String[] args) {
    m1(new X());
    m2(new com.eomcs.oop.ex08.b.sub.Y());
    m3(new Exam0215());
  }

  // 같은 패키지의 객체를 파라미터로 받은 경우
  static void m1(X obj) {
    //    obj.privateVar = 100; // 접근 불가!
    obj.defaultVar = 100; // OK! 같은 패키지
    obj.protectedVar = 100; // OK! 같은 패키지
    obj.publicVar = 100; // OK! 모두 접근 가능
  }

  // 다른 패키지의 객체를 파라미터로 받은 경우
  static void m2(com.eomcs.oop.ex08.b.sub.Y obj) {
    //    obj.privateVar = 100; // 접근 불가!
    //    obj.defaultVar = 100; // 접근 불가!
    //    obj.protectedVar = 100; // 접근 불가! Exam0215가 상속 받아 만든 변수가 아니다.
    obj.publicVar = 100; // OK! 모두 접근 가능
  }

  // 같은 객체를 파라미터로 받은 경우
  static void m3(com.eomcs.oop.ex08.b.Exam0215 obj) {
    // 다음 obj를 통해 접근하는 변수는
    // Exam0215 클래스의 인스턴스를 생성할 때 만든 변수이다.
    // 그러나 Exam0215 클래스에 선언된 변수가 아니라 상속 받은 변수이다.
    // 상속 받은 변수인 경우 상속 받은 클래스를 기준으로 접근 가능 여부를 따져야 한다.
    //    obj.privateVar = 100; // 접근 불가!
    //    obj.defaultVar = 100; // 접근 불가!
    obj.protectedVar = 100; // OK! Y 클래스를 상속 받아 만든 변수다.
    obj.publicVar = 100; // OK! 모두 접근 가능
  }

}

// private      : 클래스 안에서만 접근 가능
// (default)    : private + 같은 패키지 소속
// protected    : (default) + 서브 클래스로 만든 변수인 경우 서브 클래스에서 접근 가능
// public       : 모두 접근 가능
//





