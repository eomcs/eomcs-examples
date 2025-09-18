package com.eomcs.lang.ex06;

// # 흐름 제어문 - for(:) enhanced for 문
//
public class Exam0480 {
  public static void main(String[] args) {
    class User {
      String name;
      String email;
      String tel;
      boolean working;
      int age;
    }

    User user = new User();
    user.name = "홍길동";
    user.email = "hong@test.com";
    user.tel = "010-1111-2222";
    user.working = false;
    user.age = 20;

    // User 클래스는 Iterable 구현체가 아니다!!
    //    for (Object value : user) {
    //      System.out.println(value);
    //    }
  }
}


