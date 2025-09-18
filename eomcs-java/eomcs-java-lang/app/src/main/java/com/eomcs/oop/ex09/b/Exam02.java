// 인터페이스 필드
package com.eomcs.oop.ex09.b;


// 인터페이스 필드 선언
interface MyInterface2 {
  // 인터페이스 필드는 public static final 이다.
  // - 인스턴스를 생성할 수 없기 때문에 인스턴스 필드를 선언할 수 없다.
  // - 규칙이기 때문에 무조건 public 이다.
  // - 인스턴스 필드가 아니기 때문에 값을 변경할 수 없다.
  public static final int v1 = 100;

  // public, static, final 을 생략할 수 있다.
  static final int v2 = 200;
  public final int v3 = 300;
  public static int v4 = 400;

  int v5 = 500; // 모두 생략된 상태!
}

// 인터페이스 필드 사용
public class Exam02 {
  public static void main(String[] args) {

    // 인터페이스에 선언한 필드는 public static final 이기 때문에 바로 사용할 수 있다.
    System.out.println(MyInterface2.v1);
    System.out.println(MyInterface2.v2);
    System.out.println(MyInterface2.v3);
    System.out.println(MyInterface2.v4);
    System.out.println(MyInterface2.v5);

    // 인터페이스 필드는 상수 필드이기 때문에 값을 변경할 수 없다.
    //    MyInterface2.v1 = 111; // 컴파일 오류!
    //    MyInterface2.v2 = 222; // 컴파일 오류!
    //    MyInterface2.v3 = 333; // 컴파일 오류!
    //    MyInterface2.v4 = 444; // 컴파일 오류!
    //    MyInterface2.v5 = 555; // 컴파일 오류!
  }

}





