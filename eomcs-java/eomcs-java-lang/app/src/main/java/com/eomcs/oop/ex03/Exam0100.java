// # 변수의 종류
//
package com.eomcs.oop.ex03;

public class Exam0100 {

  // static 필드 = 클래스 필드(변수)
  // - 클래스를 로딩할 때 Method Area 영역에 생성된다.
  // - 클래스는 단 한 번만 로딩된다.
  // - 따라서 스태틱 변수도 한 번만 생성된다.
  // - JVM을 종료할 때 메모리에서 한꺼번에 제거된다.
  static int a;

  // non-static 필드 = 인스턴스 필드
  // - new 연산자를 실행할 때 Heap 영역에 생성된다.
  // - new 연산자를 실행할 때마다 생성된다.
  // - Garbage Collector에 의해 인스턴스가 해제될 때 제거된다.
  int b;

  public static void main(String[] args /* 파라미터 = 로컬 변수 */) {

    // 로컬 변수
    // - 메서드가 호출될 때 JVM Stack 영역에 생성된다.
    // - 메서드 호출이 끝나면 제거된다.
    int c;
    c = 100;

    // <=== 현재 실행 시점
    // - Method Area: a 변수 존재
    // - JVM Stack: args, c, obj 변수 존재
    // - Heap: 아직 생성된 객체 없음

    Exam0100 obj; // obj는 main()을 호출할 때 시작 시점에 JVM Stack에 생성된 상태이다.

    obj = new Exam0100();

    // <=== 현재 실행 시점
    // - Method Area: a 변수 존재
    // - JVM Stack: args, c, obj 변수 존재
    // - Heap: b 변수 존재

    System.out.println(c);

  }
}



