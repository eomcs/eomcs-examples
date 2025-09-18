package com.eomcs.oop.ex11.a;

import com.eomcs.oop.ex11.a.Exam0210.B;
import com.eomcs.oop.ex11.a.sub.A;

public class Test {
  public void m() {

    // 특정 패키지에 소속되어 있는 클래스: top level class
    // - package member class
    A obj;

    // 특정 클래스에 소속되어 있는 클래스: nested class
    // - static nested class
    // - top level class 와 동일하게 사용할 수 있다.
    Exam0210.A obj2;

    B obj3; // com.eomcs.oop.ex11.a.Exam0210.B 스태틱 중첩 클래스
  }
}
