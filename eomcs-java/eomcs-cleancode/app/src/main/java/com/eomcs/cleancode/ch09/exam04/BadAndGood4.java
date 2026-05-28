package com.eomcs.cleancode.ch09.exam04;

// 예제 4: 테스트당 하나의 개념 - UserService
public class BadAndGood4 {

  private BadAndGood4() {}

  static class InvalidEmailException extends RuntimeException {
    InvalidEmailException(String message) {
      super(message);
    }
  }

  static class WeakPasswordException extends RuntimeException {
    WeakPasswordException(String message) {
      super(message);
    }
  }

  static class User {
    private final String email;

    User(String email) {
      this.email = email;
    }

    String getEmail() { return email; }
  }

  static class UserService {
    User register(String email, String password) {
      if (!email.contains("@")) {
        throw new InvalidEmailException("잘못된 이메일 형식: " + email);
      }
      if (password.length() < 8) {
        throw new WeakPasswordException("비밀번호가 너무 짧습니다: " + password.length() + "자");
      }
      return new User(email);
    }
  }
}
