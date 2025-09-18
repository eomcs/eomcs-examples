// 인터페이스와 디폴트 메서드
package com.eomcs.oop.ex09.uc04.test4;

import com.eomcs.oop.ex09.uc04.Printer;
import com.eomcs.oop.ex09.uc04.test1.FilmPrinter;
import com.eomcs.oop.ex09.uc04.test1.PaperPrinter;

public class Exam01 {

  public static void main(String[] args) {
    // 신규 프로젝트에서 필요한 워터마킹 기능을 기존 클래스에 영향을 주지 않으면서
    // 기존 규격에 포함시키는 방법
    // => 디폴트 메서드를 추가하라!
    // => g2 패키지의 Printer 인터페이스에 watermark() 메서드는
    //    디폴트 메서드로 선언하면 된다.

    // 프린터 준비
    WaterMarkPrinter p1 = new WaterMarkPrinter();

    // 프린터를 사용하여 출력한다.
    display(p1, "안녕하세요!", "bitcamp bitcamp bitcamp bitcamp bitcamp");

    // 기존 프린터 준비!
    PaperPrinter p2 = new PaperPrinter();
    FilmPrinter p3 = new FilmPrinter();

    // 기존 규격(Printer)에 새 규칙(watermark())을 추가하더라도
    // 기존에 작성한 클래스를 그대로 활용할 수 있다.
    // 굳이 g4 패키지에서 한 것처럼 아답터 패턴을 사용할 필요도 없다.
    display(p2, "안녕하세요!", "bitcamp bitcamp bitcamp bitcamp bitcamp");
    display(p3, "안녕하세요!", "bitcamp bitcamp bitcamp bitcamp bitcamp");
  }

  private static void display(
      Printer printer, // 기존 규격을 그대로 사용하면 된다.
      String text,
      String watermarkText) {

    printer.watermark(watermarkText); // 기존 규격에 새로 추가한 메서드이다.
    printer.print(text);
    printer.watermark(watermarkText); // 기존 규격에 새로 추가한 메서드이다.
  }

}
