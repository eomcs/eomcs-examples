package com.eomcs.oop.ex01;

// # import : 사용 후
//
// - import 명령은 package 명령 다음에 와야 한다.
// - 클래스 선언 전에 와야 한다.
import com.eomcs.oop.ex01.sub.Y2;

public class Exam0720 {
  public static void main(String[] args) {

    // 다른 패키지의 클래스를 사용할 때 마다
    // 패키지명을 적는다면 코드가 너무 길어진다.
    //
    // 이를 해결하기 위해 자바는 import 라는 명령을 제공한다.
    // 클래스를 사용하기 전에 미리 해당 클래스가 어느 패키지에 있는지 지정하는 것이다.
    // 문법:
    //      import 패키지명.클래스명;
    //
    Y2 obj;
    obj = new Y2();
  }
}
