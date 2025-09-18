// 인터페이스 구현
package com.eomcs.oop.ex09.uc04.test1;

import com.eomcs.oop.ex09.uc04.Printer;

public class FilmPrinter implements Printer {
  // Printer 규칙에 따라 기능을 구현한다.
  @Override
  public void print(String text) {
    System.out.println("FilmPrinter>> " + text);
  }
}
