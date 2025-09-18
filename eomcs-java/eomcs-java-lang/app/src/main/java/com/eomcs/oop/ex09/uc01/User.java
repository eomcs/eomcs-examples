// 인터페이스 활용 : default 메서드의 활용
package com.eomcs.oop.ex09.uc01;

import com.eomcs.oop.ex09.uc01.project1.FirstComputer;
import com.eomcs.oop.ex09.uc01.project2.SecondComputer;
import com.eomcs.oop.ex09.uc01.project3.ThirdComputer;
import com.eomcs.oop.ex09.uc01.project4.NewComputer1;

public class User {
  public static void main(String[] args) {
    // 1970년대:
    play(new FirstComputer());

    // 1980년대:
    play(new SecondComputer());

    // 1990년대:
    play(new ThirdComputer());

    // 2000년대:
    play(new NewComputer1());

  }

  static void play(Computer computer) {
    computer.compute();
    System.out.println("------------------------");
  }

  // 상속을 이용하여 규칙을 추가할 때,
  // - 서브 인터페이스를 통해 새 규칙을 추가한다.
  // - 새 인터페이스에 맞는 메서드를 따로 정의해야 한다.
  static void play(Computer2 computer) {
    computer.compute();
    computer.touch();
    System.out.println("------------------------");
  }
}






