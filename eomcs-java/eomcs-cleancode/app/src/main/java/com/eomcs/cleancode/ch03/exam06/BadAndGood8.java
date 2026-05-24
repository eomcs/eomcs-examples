package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood8 {

  static class User {
    String name;
    User(String name) { this.name = name; }
  }

  // Bad
  // - user(User user) → 동사가 없어 함수가 무슨 일을 하는지 전혀 알 수 없다.
  // - 이름이 타입명과 동일해 혼란스럽다.
  static class BadUserService {
    void user(User user) {
      System.out.println("저장: " + user.name);
    }
  }

  // Good
  // - saveUser(User user) → 동사(save) + 키워드(User) 로 역할이 명확하다.
  // - 함수 이름과 인자 이름이 함께 "무엇을 저장하는가"를 설명한다.
  static class GoodUserService {
    void saveUser(User user) {
      System.out.println("저장: " + user.name);
    }
  }
}
