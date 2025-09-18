package com.eomcs.oop.ex07.step.s5;

// 추상 메서드만 있을 경우,
// 객체 사용 규칙을 정의하는 "인터페이스" 문법으로 바꿔도 좋다.
public interface Sorter {

  // 인터페이스는 호출 규칙을 정의하는 것이기 때문에
  // 모든 메서드는 기본이 public 이고, abstract 이다.
  // => 다음과 같이 메서드 선언에 public 과 abstract를 생략해도 된다.
  void sort(int[] values);
}
