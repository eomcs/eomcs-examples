package com.eomcs.oop.ex01;

// # import - java.lang 패키지
//
public class Exam0740 {
  public static void main(String[] args) {
    java.lang.Integer obj1; // OK!
    java.lang.System obj2; // OK!
    java.lang.reflect.Array obj3;
    java.io.File obj4; // OK!

    Integer obj5; // OK!
    System obj6; // OK!
    //    Array obj7; // 컴파일 오류!
    //    File obj8; // 컴파일 오류!

    // java.lang 패키지에 있는 클래스는 패키지를 지정하지 않아도 된다.
    // 즉 패키지명을 명시하지 않아도 컴파일 오류가 발생하지 않는다.
    // 그 외 다른 모든 패키지의 클래스는 패키지명을 명시하지 않으면
    // 컴파일 오류가 발생한다.
  }
}

// 결론!
// - java.lang 패키지에 소속된 클래스를 사용할 때는
//   패키지 이름을 명시하지 않아도 된다.
// - 주의!
//   - java.lang 패키지의 하위 패키지는 해당되지 않는다.
//   - 예) java.lang.annotation, java.lang.invoke 등
// - java.lang 패키지의 클래스들은 가장 많이 사용하는
//   기본 클래스이기 때문에 자바 컴파일러는 해당 클래스를 자동으로 찾는다.
// - 그 외의 모든 패키지 클래스들은 반드시 패키지명을 지정해야 한다.
// - 지정하기 싫으면 클래스 선언 전에 import 명령으로 패키지를 지정해야 한다.
//


