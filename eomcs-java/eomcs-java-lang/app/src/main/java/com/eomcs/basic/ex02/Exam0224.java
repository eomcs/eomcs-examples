// Wrapper 클래스 - 오토박싱(auto-boxing)/오토언박싱(auto-unboxing) 응용
package com.eomcs.basic.ex02;

public class Exam0224 {

  static class Member {
    String name;
    String tel;

    @Override
    public String toString() {
      return name + "," + tel;
    }
  }

  public static void main(String[] args) {

    // wrapper 클래스를 이용하면,
    // primitive data type의 값을 객체로 다룰 수 있다.
    Object obj;

    String str = new String("Hello");
    obj = str; // 다형적 변수 문법에 따라 상위 클래스의 레퍼런스에 저장할 수 있다.

    Member member = new Member();
    member.name = "홍길동";
    member.tel = "010-1111-2222";
    obj = member; // 다형적 변수 문법에 따라 상위 클래스의 레퍼런스에 저장할 수 있다.

    // 위의 코드에서 String이나 Member 처럼
    // primitive type의 값을 객체로 다룰 수 있다.
    int i = 100;
    obj = i; // auto-boxing 규칙에 따라 Integer.valueOf(i) 문장으로 변환한다.

    // obj 레퍼런스에 들어 있는 값이
    // int의 wrapper 클래스인지 확인해보자!
    System.out.println(obj instanceof Integer);
  }
}


