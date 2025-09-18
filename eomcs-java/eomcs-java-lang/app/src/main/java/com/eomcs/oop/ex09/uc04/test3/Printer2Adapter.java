package com.eomcs.oop.ex09.uc04.test3;

import com.eomcs.oop.ex09.uc04.Printer;
import com.eomcs.oop.ex09.uc04.test2.Printer2;

// 기존 작성한 클래스를 새 규격에 맞춰 재활용할 수 있게 만드는 설계 기법!
public class Printer2Adapter implements Printer2 {

  // 이 클래스는 중간에서 Printer2에 꼽을 수 있도록 중계해주는
  // 역할만 하기 때문에 이 클래스가 직접 Printer 기능을 구현해서는 안된다.
  // 대신 Printer 기능은 기존 클래스에게 맡겨야 한다.
  Printer originalPrinter;

  public Printer2Adapter(Printer originalPrinter) {
    this.originalPrinter = originalPrinter;
  }

  @Override
  public void print(String text) {
    // 아답터는 Printer2의 규격을 따르기는 하지만,
    // 직접 Printer 역할을 하는 것이 아니기 때문에
    // 아답터에게 print()라는 명령을 내리면,
    // 생성자에서 받은 기존 프린터 객체를 실행시킨다.
    originalPrinter.print(text);
  }

  @Override
  public void watermark(String title) {
    // 단 Printer2에 추가된 워터마킹 기능은 아답터에서 실행한다.
    System.out.println(title);
  }
}






