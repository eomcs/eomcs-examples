package com.eomcs.oop.ex05.x1;

// 계산기 객체를 정의한 후 사용하기
public class CalculatorTest {
  public static void main(String[] args) {
    Calculator c = new Calculator();

    c.plus(100);
    c.minus(200);

    System.out.println(c.result);
  }
}
