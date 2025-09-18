// 캡슐화 접근 범위 테스트 - 서브 클래스의 멤버가 접근할 수 있는 범위
package com.eomcs.oop.ex08.b;

public class Exam0213 extends com.eomcs.oop.ex08.b.sub.Y {

  public static void main(String[] args) {

    // 다른 패키지의 클래스를 그 클래스의 서브 클래스가 어디까지 접근할 수 있을까?
    com.eomcs.oop.ex08.b.sub.Y obj = new com.eomcs.oop.ex08.b.sub.Y();


    //    obj.privateVar = 100; // 접근 불가! 오직 그 클래스 안에서만 사용 가능.
    //    obj.defaultVar = 100; // 접근 불가! 같은 패키지까지만 접근 가능.

    //    obj.protectedVar = 100; // 접근 불가! 같은 패키지 또는 자식 클래스를 통해 만든 경우 접근 가능
    // 자식 클래스인데 접근 불가?
    // 이유
    // => 서브 클래스를 통해 만든 인스턴스 변수가 아니다.
    // => 즉 자기 변수가 아니다.

    obj.publicVar = 100; // OK! 모두 다 접근 가능.
  }
}

// private      : 클래스 안에서만 접근 가능
// (default)    : private + 같은 패키지 소속
// protected    : (default) + 서브 클래스로 만든 변수인 경우 서브 클래스에서 접근 가능
// public       : 모두 접근 가능
//