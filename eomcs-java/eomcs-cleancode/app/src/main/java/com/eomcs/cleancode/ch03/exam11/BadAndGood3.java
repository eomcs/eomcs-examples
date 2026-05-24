package com.eomcs.cleancode.ch03.exam11;

import java.util.List;

public class BadAndGood3 {

  static class User {
    private String email;
    private boolean active;
    User(String email, boolean active) {
      this.email = email; this.active = active;
    }
    boolean isActive() { return active; }
    boolean hasEmail() { return email != null && !email.isBlank(); }
    String getEmail() { return email; }
  }

  // Bad: 조건이 중첩될수록 들여쓰기가 깊어진다.
  // - isActive()와 hasEmail() 검사가 중첩 if로 구성되어 있다.
  // - 조건이 늘어날수록 중첩이 계속 깊어진다.
  // - 핵심 로직(이메일 출력)이 안쪽 깊은 곳에 묻힌다.
  static class BadUserPrinter {
    void printActiveUsers(List<User> users) {
      for (User user : users) {
        if (user.isActive()) {
          if (user.hasEmail()) {
            System.out.println(user.getEmail()); // 핵심 로직이 깊이 중첩됨
          }
        }
      }
    }
  }

  // Good: continue로 조기 탈출하여 중첩을 줄인다.
  // - 조건을 반전시켜 continue로 건너뜀으로써 중첩 없이 흐름이 위에서 아래로 읽힌다.
  // - 핵심 로직(이메일 출력)이 가장 바깥에 위치하여 눈에 잘 띈다.
  // - 조건이 추가되어도 continue만 하나씩 늘리면 되어 확장이 쉽다.
  static class GoodUserPrinter {
    void printActiveUsers(List<User> users) {
      for (User user : users) {
        if (!user.isActive()) {
          continue;
        }
        if (!user.hasEmail()) {
          continue;
        }
        System.out.println(user.getEmail());
      }
    }
  }
}
