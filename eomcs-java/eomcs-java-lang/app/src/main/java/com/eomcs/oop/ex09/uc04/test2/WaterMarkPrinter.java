// 신규 프로젝트를 위해 새로 규칙을 추가한 인터페이스를 구현
package com.eomcs.oop.ex09.uc04.test2;

public class WaterMarkPrinter implements Printer2 {
  // Printer 인터페이스에 선언된 규칙을 구현한다.
  @Override
  public void print(String text) {
    System.out.println("WaterMarkPrinter: " + text);
  }

  // 신규 프로젝트에 추가한 Printer2 인터페이스의 규칙을 구현한다.
  @Override
  public void watermark(String title) {
    System.out.println("**" + title + "**");
  }
}
