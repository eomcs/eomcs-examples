package com.eomcs.cleancode.ch12.exam05;

// 예제 3: 함수 이름이 목적을 말하게 하라 - LoginChecker / User
public class BadAndGood3 {

  private BadAndGood3() {}

  static class User {

    private final int loginFailCount;
    private final boolean locked;
    private final boolean emailVerified;

    User(int loginFailCount, boolean locked, boolean emailVerified) {
      this.loginFailCount = loginFailCount;
      this.locked = locked;
      this.emailVerified = emailVerified;
    }

    int getLoginFailCount() { return loginFailCount; }
    boolean isLocked() { return locked; }
    boolean isEmailVerified() { return emailVerified; }

    // Good: 세부 조건도 이름으로 의도를 표현한다
    boolean hasAcceptableLoginFailures() { return loginFailCount < 5; }
    boolean isNotLocked() { return !locked; }
  }

  // Bad: 무엇을 확인하는지 check()만으로 알 수 없다
  //   - 조건의 결과가 어떤 의미인지 호출부에서 추측해야 한다
  static class BadLoginChecker {

    public boolean check(User user) {
      return user.getLoginFailCount() < 5
          && !user.isLocked()
          && user.isEmailVerified();
    }
  }

  // Good: canLogin()이 목적을 말한다
  //   - 코드가 설명문처럼 읽힌다
  static class LoginChecker {

    public boolean canLogin(User user) {
      return user.hasAcceptableLoginFailures()
          && user.isNotLocked()
          && user.isEmailVerified();
    }
  }
}
