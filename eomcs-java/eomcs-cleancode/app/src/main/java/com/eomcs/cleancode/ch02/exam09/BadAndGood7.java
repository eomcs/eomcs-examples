package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood7 {

  static class User {
    String username;
    String password;
  }

  // Bad
  // - check() → 무엇을 체크하는가? 내부를 보지 않으면 의미를 전혀 알 수 없다.
  static class BadAuthService {
    boolean check(User user) {
      return user.username != null && user.password != null;
    }
  }

  // Good
  // - validateUserCredentials → 체크 대상(UserCredentials)과 목적(validate)을 명확히 표현한다.
  static class GoodAuthService {
    boolean validateUserCredentials(User user) {
      return user.username != null && user.password != null;
    }
  }
}
