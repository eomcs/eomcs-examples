// 인터페이스 규칙에 따라 만든 클래스를 사용하기
package com.eomcs.oop.ex09.a2.after;

public class Exam02 {

  public static void main(String[] args) {
    // 도구 사용하기

    // Spec 규칙(interface)에 따라 만든 도구(클래스)를 use() 메서드에 넘긴다.
    use(new ToolA());
    use(new ToolB());

    // 새로 추가한 도구(클래스 ToolC)도 Spec 규칙에 따라 만들었기 때문에
    // use() 메서드에 그대로 넘길 수 있다.
    use(new ToolC());

    //    use(new ToolD()); // 컴파일 오류!
    // 비록 ToolD 클래스에 Spec에 정의된 m1() 메서드가 있다 하더라도
    // 문법적으로 ToolD 클래스는 Spec 인터페이스를 구현한 것이 아니기 때문에
    // use 파라미터 값으로 넘길 수 없다.
  }

  static void use(Spec tool) {
    // 파라미터 tool에 넘어오는 객체는 Spec 규칙에 따라 만든 객체일 것이다.
    // Spec 규칙에 따라 만든 도구를 사용할 때는
    // Spec 규칙에 따라 일관된 방식으로 사용(메서드를 호출)하면 된다.
    // 그러면 해당 인스턴스의 클래스를 찾아 그 클래스에서 구현한 메서드를 호출할 것이다.
    tool.m1();
  }

}







