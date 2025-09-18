// static nested class : 다른 멤버에 접근하기
package com.eomcs.oop.ex11.b;

class B2 {
  // 클래스 멤버
  static int v1;
  static void m1() {}

  // 인스턴스 멤버
  int v2;
  void m2() {}

  static class X {
    void test() {
      // 일반적으로 클래스의 스태틱 멤버(필드,메서드)에 접근할 때는
      // 다음과 같이 그 클래스의 이름을 명시해야 한다.
      B2.v1 = 100;
      B2.m1();

      // 클래스 X도 B2의 test() 메서드처럼 B2 클래스의 멤버이기 때문에 
      // B2 클래스의 멤버에 접근할 때는 
      // 다음과 같이 바깥 클래스 이름을 생략할 수 있다.
      v1 = 200; 
      m1(); 

      // 스태틱 중첩 클래스도 test() 처럼 스태틱 멤버이기 때문에 
      // 바깥 클래스의 인스턴스 주소를 담는 B2.this 라는 인스턴스 멤버가 없다.
      // 따라서 바깥 클래스의 인스턴스 멤버에 접근할 수 없다.
      //      v2 = 100; // 컴파일 오류!
      //      m2(); // 컴파일 오류!
    }
  }
}

public class Exam0211 {

  public static void main(String[] args) {
    B2.X obj = new B2.X();
    obj.test();
  }

}
