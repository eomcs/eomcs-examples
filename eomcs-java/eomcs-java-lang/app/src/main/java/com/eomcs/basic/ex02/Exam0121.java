// String - String 인스터스에 있는 값을 비교하기 : equalsIgnoreCase()
package com.eomcs.basic.ex02;

public class Exam0121 {
  public static void main(String[] args) {

    String s1 = new String("Hello");
    String s2 = new String("HELLO");

    // equals()는 대소문자를 구분한다.
    System.out.println(s1.equals(s2));

    // 대소문자 구분없이 문자열을 비교하고 싶다면,
    System.out.println(s1.equalsIgnoreCase(s2));
  }
}


