// 인터페이스 레퍼런스와 인스턴스의 관계
package com.eomcs.oop.ex09.a2.after;

public class Exam01 {

  public static void main(String[] args) {

    // 인터페이스 레퍼런스 선언
    // => Spec 인터페이스를 구현한 클래스의 인스턴스 주소를 저장하겠다는 의미다.
    // => Spec 규칙에 따라 작성한 클래스의 객체를 담겠다는 의미다.
    // => Spec 객체를 담겠다는 의미다.
    Spec tool;

    // ToolA 클래스는 Spec 인터페이스의 규칙에 따라 만든 클래스이기 때문에
    // 이 클래스의 인스턴스 주소를 tool 레퍼런스 변수에 저장할 수 있다.
    tool = new ToolA();

    // ToolB 클래스도 ToolA와 마찬가지로
    // Spec 구현체(implementer; 인터페이스 규칙에 따라 만든 클래스)이기 때문에
    // 해당 객체를 저장할 수 있다.
    tool = new ToolB();

    tool = new ToolC();

    //    tool = new ToolD(); // ToolD 클래스는 Spec 규칙을 준수하지 않는다.

    // String 클래스는 Spec 구현체가 아니기 때문에 해당 객체를 레퍼런스에 저장할 수 없다.
    //    tool = new String("Hello"); // 컴파일 오류!
  }
}







