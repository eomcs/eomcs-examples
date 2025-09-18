// 클래스 변수의 응용
//
package com.eomcs.oop.ex03;

public class Exam0162 {

  static class Member {

    // Member와 관련된 값을 갖고 있는 변수라면
    // 다음과 같이 Member 클래스에 두는 것이 유지보수에 좋다.
    // - 여러 인스턴스에서 공통으로 사용할 값을 담는 변수라면 static 필드로 선언하라!
    // - 조회용으로 사용할 변수이면서 외부에서도 사용해야 할 변수라면 public 으로 공개하라!
    // - 다음 변수는 클래스 변수(스태틱 변수)의 전형적인 예이다.
    public static final int GUEST = 0;
    public static final int MEMBER = 1;
    public static final int MANAGER = 2;

    String id;
    String password;
    int type; // 0: 손님, 1: 회원, 2: 관리자
  }

  public static void main(String[] args) {

    // Member와 관련된 상수라면
    // Member 클래스에 선언하는 것이 유지보수에 더 낫다.
    //    final int GUEST = 0;
    //    final int MEMBER = 1;
    //    final int MANAGER = 2;

    Member m4 = new Member();
    m4.id = "aaa";
    m4.password = "1111";
    m4.type = Member.GUEST;
    // 스태틱 변수는 변수명 앞에 클래스명을 명시해야 한다.

    Member m5 = new Member();
    m5.id = "bbb";
    m5.password = "1111";
    m5.type = Member.MANAGER;

    Member m6 = new Member();
    m6.id = "ccc";
    m6.password = "1111";
    m6.type = Member.MEMBER;
  }
}








