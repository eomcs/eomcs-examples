// 예외 처리 문법을 적용한 후 - 메서드가 던지는 예외 정보를 받는다.
package com.eomcs.exception.ex2;

public class Exam0110 {
  public static void main(String[] args) {
    String op = "#";
    int a = 100;
    int b = 200;

    // 리턴 값으로 예외 상황을 알리는 것이 아니라,
    // 예외 정보를 던지는 방식으로 호출자에게 알린다.
    try {
      // 예외를 던질 수 있는 메서드를 호출할 때는 try 블록 안에서 호출한다.
      // => 예외가 발생하면 리턴 값으로 받는 게 아니라 catch 블록에서 따로 받는다.
      int result = Calculator3.compute(op, a, b);
      System.out.println(result);

    } catch (Throwable e) {
      // try 블록 안에서 메서드를 호출하다가 예외가 발생하면
      // catch 블록에서 예외 객체를 파라미터로 받는다.
      System.out.println(e.getMessage());
    }
  }
}


