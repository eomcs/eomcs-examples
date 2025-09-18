// 인터페이스 구현 - 인터페이스에 선언된 모든 메서드를 정의해야 한다.
package com.eomcs.oop.ex09.uc05;

public class MyPen implements Pen {

  boolean use;

  @Override
  public void on() {
    this.use = true;
  }

  @Override
  public void off() {
    this.use = false;
  }

  @Override
  public void write(String text) {
    if (!this.use)
      return;

    System.out.println("===> " + text);
  }

}











