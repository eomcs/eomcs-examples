package com.eomcs.cleancode.ch12.exam01;

// 예제 2: 조건식을 도메인 언어로 표현하라 - UserService / User
public class BadAndGood2 {

  private BadAndGood2() {}

  static class BadUser {

    private final boolean active;
    private final boolean locked;
    private final int loginFailCount;

    BadUser(boolean active, boolean locked, int loginFailCount) {
      this.active = active;
      this.locked = locked;
      this.loginFailCount = loginFailCount;
    }

    boolean isActive() { return active; }
    boolean isLocked() { return locked; }
    int getLoginFailCount() { return loginFailCount; }
  }

  // Bad: 조건의 의도를 읽는 사람이 해석해야 한다
  //   - 로그인 가능 조건이 도메인 개념으로 표현되지 않는다
  static class BadUserService {

    public boolean canLogin(BadUser user) {
      if (user.isActive() && !user.isLocked() && user.getLoginFailCount() < 5) {
        return true;
      }
      return false;
    }
  }

  // Good: 조건문이 도메인 언어로 바뀐다
  //   - isLoginAllowed()가 비즈니스 규칙을 표현한다
  //   - 테스트를 유지한 채 리팩토링하면 이런 구조가 자연스럽게 나온다
  static class User {

    private final boolean active;
    private final boolean locked;
    private final int loginFailCount;

    User(boolean active, boolean locked, int loginFailCount) {
      this.active = active;
      this.locked = locked;
      this.loginFailCount = loginFailCount;
    }

    boolean isLoginAllowed() {
      return isActive()
          && isNotLocked()
          && hasAcceptableLoginFailures();
    }

    boolean isActive() {
      return active;
    }

    private boolean isNotLocked() {
      return !locked;
    }

    private boolean hasAcceptableLoginFailures() {
      return loginFailCount < 5;
    }
  }

  // Good: canLogin()의 의도가 명확해진다
  //   - 조건 해석을 호출자에게 떠넘기지 않는다
  static class UserService {

    public boolean canLogin(User user) {
      return user.isLoginAllowed();
    }
  }
}
