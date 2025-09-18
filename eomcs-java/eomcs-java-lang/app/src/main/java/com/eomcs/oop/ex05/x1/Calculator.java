package com.eomcs.oop.ex05.x1;

public class Calculator {
  public int result;

  public void plus(int value) {
    this.result += value;
  }

  public void minus(int value) {
    this.result -= value;
  } 

  // com.eomcs.oop.ex05.x1.upgrade1.CalculatorTest 에서 사용할 기능을 추가한다.
  //  public void multiple(int value) {
  //    this.result *= value;
  //  }
}
