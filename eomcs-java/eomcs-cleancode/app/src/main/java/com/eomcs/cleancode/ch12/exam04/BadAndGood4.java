package com.eomcs.cleancode.ch12.exam04;

// 예제 4: 조건식 중복을 메서드로 추출하라 - UserValidator
public class BadAndGood4 {

  private BadAndGood4() {}

  record User(String email, String password) {}

  // Bad: 이메일 검증 로직이 중복된다
  //   - 이메일 정책이 바뀌면 두 곳 모두 수정해야 한다
  //   - 한 곳을 빠뜨리면 검증 기준이 달라진다
  static class BadUserValidator {

    public boolean isValidForSignup(User user) {
      return user.email() != null
          && user.email().contains("@")
          && user.password() != null
          && user.password().length() >= 8;
    }

    public boolean isValidForPasswordReset(User user) {
      return user.email() != null
          && user.email().contains("@");
    }
  }

  // Good: 이메일 검증 규칙이 한 곳에 모인다
  //   - 메서드 이름이 의도를 드러낸다
  //   - 중복 제거와 표현력 향상이 함께 일어난다
  static class UserValidator {

    public boolean isValidForSignup(User user) {
      return hasValidEmail(user)
          && hasValidPassword(user);
    }

    public boolean isValidForPasswordReset(User user) {
      return hasValidEmail(user);
    }

    private boolean hasValidEmail(User user) {
      return user.email() != null
          && user.email().contains("@");
    }

    private boolean hasValidPassword(User user) {
      return user.password() != null
          && user.password().length() >= 8;
    }
  }
}
