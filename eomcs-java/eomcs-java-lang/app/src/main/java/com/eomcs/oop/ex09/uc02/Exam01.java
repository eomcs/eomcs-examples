// 인터페이스의 활용
package com.eomcs.oop.ex09.uc02;

public class Exam01 {
  public static void main(String[] args) {

    // Pen 사용 규칙에 따라 만든 도구 준비
    BallPen153 t1 = new BallPen153();
    MultiPen t2 = new MultiPen();

    // Pen이라는 도구를 사용하는 Canvas를 만든다.
    Canvas c1 = new Canvas(t1);

    // 캔버스를 실행한다.
    // 캔버스는 생성자에서 주입 받은 펜을 사용하여 텍스트를 그릴 것이다.
    // 쓰기 전에 먼저 볼펜을 사용할 수 있게 볼심을 밖으로 내민다.
    t1.press(true);

    c1.render("오호라... 펜으로 그린 것!");

    // 볼펜을 교체한다.
    c1.setPen(t2);
    //
    //    // 멀티펜을 사용할 수 있게 한다.
    t2.rotate(Zebra.ON);
    //
    //    // 그리고 그 펜을 사용하여 그린다.
    c1.render("오호라... 펜으로 그린 것!");

  }
}








