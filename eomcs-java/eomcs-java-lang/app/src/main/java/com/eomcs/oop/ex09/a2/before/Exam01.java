// 인터페이스 사용 전
package com.eomcs.oop.ex09.a2.before;

public class Exam01 {

  public static void main(String[] args) {

    // 객체의 기능을 사용해보자!
    // => 각 도구(객체)의 사용법이 다르기 때문에 각 도구에 맞춰서 기능을 사용해야 한다.
    // => 그래서 각 도구에 맞는 use() 메서드를 각각 따로 준비했다.

    // 1) ToolA 객체 사용하기
    use(new ToolA());

    // 2) ToolB 객체 사용하기
    use(new ToolB());
  }

  static void use(ToolA tool) {
    // ToolA 객체를 사용하려면 m1() 메서드를 호출해야 한다.
    tool.m1();
  }

  static void use(ToolB tool) {
    // ToolB 객체를 사용하려면 m2() 메서드를 호출해야 한다.
    tool.m2();
  }

}







