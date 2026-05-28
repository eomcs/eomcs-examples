package com.eomcs.cleancode.ch10.exam01;

// 예제 3: 클래스 체계 - UserValidator
public class BadAndGood3 {

  private BadAndGood3() {}

  static class User {
    private final String email;

    User(String email) {
      this.email = email;
    }

    String email() { return email; }
  }

  // Bad: 테스트를 위해 내부 메서드를 public/protected로 열어 캡슐화 파괴
  static class BadUserValidator {

    public boolean hasValidEmail(User user) {  // 내부 보조 메서드가 public으로 노출
      return user.email() != null && user.email().contains("@");
    }

    protected boolean isBlockedDomain(String email) {  // 테스트 때문에 protected로 열림
      return email.endsWith("@spam.com");
    }

    public boolean validate(User user) {
      return hasValidEmail(user) && !isBlockedDomain(user.email());
    }
  }

  // Good: 보조 메서드는 private, 테스트는 public 행위만 검증
  static class UserValidator {

    public boolean validate(User user) {
      return hasValidEmail(user) && !isBlockedDomain(user.email());
    }

    private boolean hasValidEmail(User user) {
      return user.email() != null && user.email().contains("@");
    }

    private boolean isBlockedDomain(String email) {
      return email.endsWith("@spam.com");
    }
  }
}
