// final 사용법: 상수 필드 만들기
package com.eomcs.oop.ex06.e;

class D {
  // 변수 초기화 문장으로 값을 초기화시킬 수 있다.
  // => 변수 초기화 문장은 컴파일 될 때 생성자로 복사되기 때문이다.
  final int v1 = 100;

  public D() {
    // 초기화 문장에서 값을 설정했으면,
    // 생성자에서 다시 값을 설정할 수 없다.
    // 왜?
    // - 초기화 문장은 결국 컴파일할 때
    //   생성자의 첫 번째 문장으로 옮겨지기 때문이다.
    //    v1 = 200; // 컴파일 오류!
  }
}
public final class Exam0320 {
  public static void main(String[] args) {
    D d = new D();
    System.out.println(d.v1);
  }
}
