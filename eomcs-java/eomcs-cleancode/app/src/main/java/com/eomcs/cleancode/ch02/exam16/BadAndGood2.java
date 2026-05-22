package com.eomcs.cleancode.ch02.exam16;

public class BadAndGood2 {

  // Bad
  // - User 클래스 안에서 user 접두사가 모든 필드에 반복된다.
  // - 이미 User 클래스 안에 있으므로 user 접두사는 불필요한 중복이다.
  static class BadUser {
    String userName;
    String userEmail;
    int userAge;
  }

  // Good
  // - 클래스가 이미 User라는 맥락을 제공한다.
  // - 변수는 핵심 정보(name, email, age)만 표현하면 충분하다.
  static class User {
    String name;
    String email;
    int age;
  }
}
