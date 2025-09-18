// Object 클래스의 메서드를 오버라이딩 하기 - equals()
package com.eomcs.basic.ex01;

public class Exam0132 {

  static class Member /*extends Object */{
    String name;
    int age;

    public Member(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  public static void main(String[] args) {

    Member m1 = new Member("홍길동", 20);
    Member m2 = new Member("홍길동", 20);

    System.out.println(m1 == m2);

    // equals()
    // => 이 메서드는 Object에 정의된 메서드이다.
    //    따라서 자바의 모든 클래스는 이 메서드를 사용할 수 있다.
    // => 두 개의 인스턴스가 같은 인스턴스인지 비교한다.
    // => == 연산자와 동일하게 동작한다.
    //
    System.out.println(m1.equals(m2));
    // m1과 m2는 다른 인스턴스이기 때문에 결과는 당연히 false 이다.

    System.out.println("-----------------------");

    String s1 = new String("Hello");
    String s2 = new String("Hello");

    System.out.println(s1 == s2);

    System.out.println(s1.equals(s2));
    // 위의 실행 결과를 보면 s1과 s2가 서로 다른 인스턴스인데도 불구하고
    // true를 리턴한다.
    // 이유?
    // => String 클래스에서 Object의 equals()를 오버라이딩 했기 때문이다.
    // => 인스턴스가 다르더라도 문자열이 같으면 true를 리턴하도록
    //    equals() 메서드를 재정의하였다.
    // => 그래서 String에 대해 equals()를 호출하면
    //    Member와 달리 true를 리턴한다.
    //
    // Member 객체에 대해서도 인스턴스가 다르더라도
    // 데이터가 같으면 true를 리턴하도록 하고 싶은가?
    // => String 클래스처럼 equals()을 오버라이딩 하라!
    // => Exam0133.java를 보라!

  }
}








