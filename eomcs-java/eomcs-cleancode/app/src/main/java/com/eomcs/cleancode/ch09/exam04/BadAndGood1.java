package com.eomcs.cleancode.ch09.exam04;

// 예제 1: 테스트당 하나의 개념 - User
public class BadAndGood1 {

  private BadAndGood1() {}

  static class User {
    private final String name;
    private final String email;
    private final int age;

    User(String name, String email, int age) {
      this.name = name;
      this.email = email;
      this.age = age;
    }

    String getName() { return name; }
    String getEmail() { return email; }
    int getAge() { return age; }
  }
}
