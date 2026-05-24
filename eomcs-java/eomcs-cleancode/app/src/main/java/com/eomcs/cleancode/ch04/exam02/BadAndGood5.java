package com.eomcs.cleancode.ch04.exam02;

public class BadAndGood5 {

  static class User {
    private String email;
    User(String email) { this.email = email; }
    String getEmail() { return email; }
  }

  static class Database {
    void save(User user) { System.out.println("DB 저장: " + user.getEmail()); }
  }

  // Bad
  // - null 검사와 이메일 형식 검사가 직접 노출되어 조건이 길고 복잡하다.
  // - "// validate user" 주석이 코드 대신 의도를 설명하고 있다.
  // - 조건이 추가될수록 if 문이 점점 더 읽기 어려워진다.
  static class BadUserService {
    Database database = new Database();

    void process(User user) {
      // validate user
      if (user != null &&
          user.getEmail() != null &&
          user.getEmail().contains("@")) {
        database.save(user);
      }
    }
  }

  // Good: 복잡한 검증 조건을 의미 있는 메서드로 추출한다.
  // - isValidUser()라는 이름이 조건 전체의 목적을 한 단어로 표현한다.
  // - isValidEmail()로 이메일 규칙을 별도로 분리하여 재사용과 수정이 쉽다.
  // - 주석 없이도 코드를 읽는 순간 의도를 파악할 수 있다.
  static class GoodUserService {
    Database database = new Database();

    void process(User user) {
      if (isValidUser(user)) {
        database.save(user);
      }
    }

    private boolean isValidUser(User user) {
      return user != null && isValidEmail(user.getEmail());
    }

    private boolean isValidEmail(String email) {
      return email != null && email.contains("@");
    }
  }
}
