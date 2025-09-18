// Reflection API : 클래스 로딩
package com.eomcs.reflect.ex01;

class A {
  static int i;

  static void m() {
    i = 100;
    System.out.println(i);
  }

  static {
    System.out.println("A 클래스 로딩!");
  }

  public A() {
    System.out.println("A() 생성자 호출!");
  }
}


public class Exam0110 {

  public static void main(String[] args) throws Exception {
    // 클래스 로딩
    // => 클래스가 로딩되어 있지 않을 때 다음을 수행하면 클래스가 로딩된다.
    // 1) 클래스의 스태틱 멤버(변수,메서드)를 사용할 때
    // 2) new 명령을 사용하여 인스턴스를 생성하려 할 때
    // 3) Class.forName()을 이용하여 임의로 클래스를 로딩할 때
    //
    // 클래스 로딩 과정에서 하는 일
    // => 스태틱 변수를 준비한다.
    // => 스태틱 블록을 실행한다.

    //    A.i = 100; // 클래스 로딩 확인!
    //    A.m(); // 클래스 로딩 확인!
    //    new A(); // 클래스 로딩 확인!
    //    new A(); // 클래스는 중복으로 로딩되지 않는다.

    //    Class.forName("com.eomcs.reflect.ex01.A");
    // 파라미터로 패키지명을 포함한 전체 클래스 이름을 지정해야 한다.
    // 패키지명을 포함한 전체 클래스 이름
    // = fully qualified class name
    // = FQName
    // = QName

    //    A obj = null; // 레퍼런스 선언은 클래스 로딩과 상관 없다.
    //    A[] arr; // 배열 레퍼런스 선언도 클래스 로딩과 상관 없다.
    //    arr = new A[100]; // 레퍼런스 배열도 마찬가지로 클래스 로딩과 상관없다.

    System.out.println("실행 했음!");
  }

}


