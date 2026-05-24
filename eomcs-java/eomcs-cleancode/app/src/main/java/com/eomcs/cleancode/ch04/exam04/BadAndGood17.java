package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood17 {

  static class User {
    private String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: 함수 헤더 (Function Headers)
  // - "// This function saves the user"는 메서드 이름 saveUser()가 이미 말하는 내용이다.
  // - 짧고 명확한 함수는 설명 주석이 필요 없다.
  // - 이름을 잘 붙인 함수가 주석으로 헤더를 추가한 함수보다 훨씬 좋다.
  static class BadUserRepository {
    // This function saves the user
    public void saveUser(User user) {
      System.out.println("저장: " + user.getName());
    }
  }

  // Good: 이름이 명확한 함수는 헤더 주석이 불필요하다.
  // - saveUser()라는 이름 하나가 함수 헤더 주석보다 훨씬 더 명확하다.
  static class GoodUserRepository {
    public void saveUser(User user) {
      System.out.println("저장: " + user.getName());
    }
  }
}
