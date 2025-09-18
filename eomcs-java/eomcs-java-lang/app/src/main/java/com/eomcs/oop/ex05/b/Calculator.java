// 기존의 클래스에 코드를 추가한다.
package com.eomcs.oop.ex05.b;

public class Calculator {

  public int result;

  public void plus(int value) {
    this.result += value;
  }

  public void minus(int value) {
    this.result -= value;
  }

  // 새 기능을 기존 클래스에 추가한다.
  //
  public void multiple(int value) {
    this.result *= value;
  }

  public void divide(int value) {
    this.result /= value;
  }
}
