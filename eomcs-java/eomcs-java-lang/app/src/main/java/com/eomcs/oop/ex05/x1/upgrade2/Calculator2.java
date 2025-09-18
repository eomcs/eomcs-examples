package com.eomcs.oop.ex05.x1.upgrade2;

// 기존의 Calculator 클래스를 복제한 후 기능을 추가한다.
public class Calculator2 {
  int result;

  public void plus(int value) {
    this.result += value;
  }

  public void minus(int value) {
    this.result -= value;
  }

  public void multiple(int value) {
    this.result *= value;
  }
}
