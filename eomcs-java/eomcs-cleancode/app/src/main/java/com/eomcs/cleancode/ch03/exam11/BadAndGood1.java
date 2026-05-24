package com.eomcs.cleancode.ch03.exam11;

public class BadAndGood1 {

  static class User {
    private String name;
    private boolean active;
    User(String name, boolean active) { this.name = name; this.active = active; }
    String getName() { return name; }
    boolean isActive() { return active; }
  }

  // Bad (구조적 프로그래밍 관점)
  // - return이 여러 곳에 있으므로 단일 출구 원칙을 위반한다.
  // - 그러나 이 함수는 작고 단순하여 오히려 읽기가 쉽다.
  // - Clean Code는 함수가 충분히 작다면 여러 return을 허용한다.
  static class MultiReturnUserValidator {
    boolean isValidUser(User user) {
      if (user == null) {
        return false;
      }
      if (user.getName() == null) {
        return false;
      }
      if (!user.isActive()) {
        return false;
      }
      return true;
    }
  }

  // Good (구조적 프로그래밍 방식: 단일 return)
  // - return은 하나만 존재하므로 단일 출구 원칙을 준수한다.
  // - 하지만 valid 변수를 계속 추적해야 하고, 조건 흐름이 더 복잡해 보인다.
  // - 작은 함수에서는 오히려 가독성이 떨어질 수 있다.
  // 👉 핵심: 함수를 작게 유지하면 여러 return이 문제가 되지 않는다.
  static class SingleReturnUserValidator {
    boolean isValidUser(User user) {
      boolean valid = true;

      if (user == null) {
        valid = false;
      } else if (user.getName() == null) {
        valid = false;
      } else if (!user.isActive()) {
        valid = false;
      }

      return valid;
    }
  }
}
