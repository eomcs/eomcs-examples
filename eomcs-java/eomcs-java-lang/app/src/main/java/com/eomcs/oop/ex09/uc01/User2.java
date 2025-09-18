// 인터페이스 활용 : default 메서드의 활용
package com.eomcs.oop.ex09.uc01;

import com.eomcs.oop.ex09.uc01.project1.FirstComputer;
import com.eomcs.oop.ex09.uc01.project2.SecondComputer;
import com.eomcs.oop.ex09.uc01.project3.ThirdComputer;
import com.eomcs.oop.ex09.uc01.project4.NewComputer2;

public class User2 {
  public static void main(String[] args) {
    // 1970년대:
    play(new FirstComputer());

    // 1980년대:
    play(new SecondComputer());

    // 1990년대:
    play(new ThirdComputer());

    // 2000년대:
    play(new NewComputer2());

  }

  // 새 규칙을 추가할 때,
  // - 기존 인터페이스를 계속 사용할 수 있다.
  static void play(Computer computer) {
    computer.compute();
    computer.touch();
    System.out.println("------------------------");
  }

}






