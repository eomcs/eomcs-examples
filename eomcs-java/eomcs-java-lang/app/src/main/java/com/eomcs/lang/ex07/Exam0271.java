package com.eomcs.lang.ex07;

// # 메서드 : 가변 파라미터의 단점
//
public class Exam0271 {

  static void m2(int a, String... names) {} // OK!

  // 배열 파라미터는 여러 개 선언할 수 있다.
  static void x1(String[] names, String[] emails) {}

  // 배열 파리미터는 순서에 상관 없다.
  static void x2(String[] names, int a) {}

  public static void main(String[] args) {
    // 컴파일 확인하라!
  }
}

// 결론!
// - 메서드에 가변 파라미터는 한 개만 사용할 수 있다.
// - 가변 파라미터는 반드시 맨 뒤에 와야 한다.
// - 그 이유는 복잡한 사용을 막기 위해!


