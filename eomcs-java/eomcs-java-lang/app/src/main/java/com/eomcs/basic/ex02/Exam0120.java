// String - String 인스터스에 있는 값을 비교하기 : equals()
package com.eomcs.basic.ex02;

public class Exam0120 {
  public static void main(String[] args) {

    String s1 = new String("Hello");
    String s2 = new String("Hello");

    // 두 String 인스턴스는 분명히 서로 다르다.
    System.out.println(s1 == s2);

    // 두 인스턴스가 갖고 있는 문자열이 같은지를 비교하고 싶다면,
    System.out.println(s1.equals(s2));

    // equals()?
    // - Object에 정의되어 있는 메서드이다.
    // - 인스턴스가 같은지 비교한다.
    //
    // String의 equals()?
    // - Object에서 상속 받은 것을 오버라이딩하였다.
    // - 문자열이 같은지 비교한다.
    //
  }
}


