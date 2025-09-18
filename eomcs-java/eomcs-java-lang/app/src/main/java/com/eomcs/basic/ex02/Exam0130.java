// String - hashCode()
package com.eomcs.basic.ex02;

public class Exam0130 {

  static class Member {
    String name;
    int age;

    public Member(String n, int a) {
      name = n;
      age = a;
    }
  }

  public static void main(String[] args) {

    Member m1 = new Member("aaa", 20);
    Member m2 = new Member("aaa", 20);

    // 인스턴스가 다르다.
    System.out.println(m1 == m2);

    // Object의 hashCode()는 인스턴스 마다 다르다.
    System.out.println(m1.hashCode() == m2.hashCode());

    System.out.println("------------------------------");

    String s1 = new String("Hello");
    String s2 = new String("Hello");

    // 두 String 의 인스턴스가 다르다.
    System.out.println(s1 == s2);

    // String의 hashCode()?
    System.out.println(s1.hashCode() == s2.hashCode()); // true
    //
    // 그러나, String의 hashCode()은
    // 문자열이 같으면 같은 hashCode()를 리턴하도록 오버라이딩 하였다.
    // 이유?
    // - 문자열이 같은 경우 같은 객체로 다루기 위함이다.
    // - HashSet 에서 객체를 저장할 때 이 메서드의 리턴 값으로 저장 위치를 계산한다.
    // - HashMap이나 Hashtable에서는 Key를 다룰 때 이 메서드의 리턴 값을 사용한다.
    // - 보통 hashCode()와 equals()를 함께 오버라이딩 한다.

    // String 클래스는 toString()도 오버라이딩 했다.
    System.out.println(s1.toString());
  }
}


