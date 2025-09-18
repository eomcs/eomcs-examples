// anonymous class - 수퍼 클래스의 메서드 오버라이딩 하기
package com.eomcs.oop.ex11.e;

public class Exam0310 {

  static class A {
    public void print() {
      System.out.println("반갑습니다!");
    }
  }

  public static void main(final String[] args) {
    A obj = new A() {
      @Override
      public void print() {
        System.out.println("정말 반갑습니다!");
      }

      public void m() {

      }
    };
    obj.print(); // 컴파일 OK! print()는 A의 메서드이다. JVM이 호출할 때는 실제 메서드를 호출한다. 

    //    obj.m(); // 컴파일 오류! 왜? obj의 타입에 정의된 메서드가 아니다. 
  }
}
