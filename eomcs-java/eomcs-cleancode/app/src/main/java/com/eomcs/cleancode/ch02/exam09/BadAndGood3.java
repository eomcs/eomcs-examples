package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood3 {

  // Bad
  // - active(), permission() → true/false 의 의미가 불명확하다.
  // - 반환값이 무엇을 의미하는지 이름만으로 알 수 없다.
  static class BadUser {
    boolean active;
    String role;

    boolean active() { return active; }
    boolean permission() { return role != null; }
  }

  // Good
  // - is/has/can/should 접두사 사용 → 읽을 때 자연스럽게 해석된다.
  // - "isActive?" → 활성 상태인가? 처럼 의도가 명확히 드러난다.
  static class GoodUser {
    boolean active;
    String role;
    int retryCount;

    boolean isActive() { return active; }
    boolean hasPermission() { return role != null; }
    boolean canExecute() { return active && role != null; }
    boolean shouldRetry() { return retryCount < 3; }
  }
}
