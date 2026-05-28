package com.eomcs.cleancode.ch12.exam03;

// 예제 2: 의도를 표현하라 - UserService / User
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

  // Bad: 조건은 맞지만 의미를 해석해야 한다
  //   - "로그인 가능 조건"이라는 도메인 개념이 코드에 없다
  static class BadUserService {

    public boolean canLogin(BadUser user) {
      return user.isActive()
          && !user.isLocked()
          && user.getLoginFailCount() < 5;
    }
  }

  // Good: 조건식이 도메인 언어로 바뀐다
  //   - isLoginAllowed()가 의도를 드러낸다
  //   - 읽는 사람이 구현보다 의미를 먼저 이해한다
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

    boolean isActive() { return active; }

    private boolean isNotLocked() { return !locked; }

    private boolean hasAcceptableLoginFailures() { return loginFailCount < 5; }
  }

  static class UserService {

    public boolean canLogin(User user) {
      return user.isLoginAllowed();
    }
  }
}
