package com.eomcs.cleancode.ch09.exam05;

// 예제 4: S — Self-Validating (스스로 검증) - User
public class BadAndGood4 {

  private BadAndGood4() {}

  static class User {
    private final String name;

    User(String name) {
      this.name = name;
    }

    String getName() { return name; }
  }
}
