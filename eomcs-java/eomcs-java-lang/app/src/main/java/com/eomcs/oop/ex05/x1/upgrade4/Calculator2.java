package com.eomcs.oop.ex05.x1.upgrade4;

public class Calculator2 {

  // plus(), minus()는 기존의 Calculator 클래스에게 위임한다.
  com.eomcs.oop.ex05.x1.Calculator origin = new com.eomcs.oop.ex05.x1.Calculator();

  public void plus(int value) {
    // 이 클래스가 포함하고 있는 객체에게 실행을 위임한다.
    origin.plus(value);
  }

  public void minus(int value) {
    // 이 기능은 기존의 클래스가 처리하도록 기존 객체에게 위임한다.
    origin.minus(value);
  }

  // 새 기능 또한 기존 객체의 필드를 사용하여 처리한다.
  public void multiple(int value) {
    origin.result *= value;
  }

  public int getResult() {
    return origin.result;
  }
}









