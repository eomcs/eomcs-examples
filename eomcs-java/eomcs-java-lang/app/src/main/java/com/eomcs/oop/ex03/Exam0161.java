// 클래스 변수의 응용
//
package com.eomcs.oop.ex03;

public class Exam0161 {

  static class Member {
    String id;
    String password;
    int type; 
  }

  public static void main(String[] args) {

    // 이전 예제의 문제점을 해결하기 위해 나온 것이 상수이다.
    // - 즉 의미있는 이름으로 변수를 만들어 놓고 그 변수에 숫자를 저장한다.
    // - 그리고 그 값을 사용할 때는 변수를 통해 사용한다.
    // - 조회 용으로 사용할 변수이기에 final 로 선언한다.
    // - 상수라는 것을 직관적으로 알리기 위해 변수 이름을 대문자로 선언한다.
    //
    final int GUEST = 0;
    final int MEMBER = 1;
    final int MANAGER = 2;

    // 다음은 상수를 사용한 예이다.
    Member m4 = new Member();
    m4.id = "aaa";
    m4.password = "1111";
    m4.type = GUEST; // 숫자로 타입을 지정하는 대신 변수명을 사용했기 때문에 따로 주석을 달 필요가 없다.

    Member m5 = new Member();
    m5.id = "bbb";
    m5.password = "1111";
    m5.type = MANAGER;

    Member m6 = new Member();
    m6.id = "ccc";
    m6.password = "1111";
    m6.type = MEMBER;
  }
}


