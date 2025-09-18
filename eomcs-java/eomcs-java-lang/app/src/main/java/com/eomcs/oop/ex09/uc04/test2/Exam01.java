package com.eomcs.oop.ex09.uc04.test2;

import com.eomcs.oop.ex09.uc04.test1.FilmPrinter;
import com.eomcs.oop.ex09.uc04.test1.PaperPrinter;

public class Exam01 {

  public static void main(String[] args) {
    // 신규 프로젝트에서는 워터마킹 기능이 있는 프린터를 사용한다.

    // 프린터 준비
    WaterMarkPrinter p1 = new WaterMarkPrinter();

    // 프린터를 사용하여 출력한다.
    display(p1, "안녕하세요!", "bitcamp bitcamp bitcamp bitcamp bitcamp");

    // 기존 프린터 준비!
    PaperPrinter p2 = new PaperPrinter();
    FilmPrinter p3 = new FilmPrinter();

    // 안타깝게도 기존 프린터는 새 프린터 규격에 맞지 않아서
    // 출력하는데 사용할 수 없다.
    // => 컴파일 오류!
    // 
    //    display(p2, "안녕하세요!", "bitcamp bitcamp bitcamp bitcamp bitcamp");
    //    display(p3, "안녕하세요!", "bitcamp bitcamp bitcamp bitcamp bitcamp");
  }

  private static void display(
      Printer2 printer,
      String text,
      String watermarkText) {

    printer.watermark(watermarkText);
    printer.print(text);
    printer.watermark(watermarkText);
  }

}
