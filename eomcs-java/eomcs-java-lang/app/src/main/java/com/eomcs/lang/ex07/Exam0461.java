package com.eomcs.lang.ex07;

//# 메서드 : 스택 오버플로우 오류!
//
public class Exam0461 {

  static int sum(int value, int value2, int value3, int value4, int value5, int value6) {
    System.out.println(value);
    if (value == 1)
      return 1;

    return value + sum(value - 1, value2, value3, value4, value5, value6);
  }

  public static void main(String[] args) {
    // 호출하는 메서드의 로컬 변수 메모리가 많을 때는 스택 메모리가 빨리 찬다.
    // => 즉 스택 오버플로우는 메서드 호출 회수에 영향을 받는 것이 아니라,
    //    메서드에서 생성하는 로컬 변수의 크기에 영향을 받는다.
    System.out.println(sum(8000, 0, 0, 0, 0, 0));
  }
}





