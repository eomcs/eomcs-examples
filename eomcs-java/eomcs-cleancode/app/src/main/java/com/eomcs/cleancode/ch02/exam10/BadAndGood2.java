package com.eomcs.cleancode.ch02.exam10;

public class BadAndGood2 {

  static class User {
    int id;
    String name;
  }

  // Bad
  // - ninjaKill → 개발자 취향을 반영한 이름으로 실제 동작과 무관하다.
  // - 유지보수 시 이름만 보고는 무슨 일을 하는지 알 수 없어 혼란이 발생한다.
  static class BadUserService {
    void ninjaKill(User user) {
      System.out.println("사용자 삭제: " + user.name);
    }
  }

  // Good
  // - deleteUser → 이름과 동작이 일치한다.
  // - 코드만 보고도 즉시 이해 가능하다.
  static class GoodUserService {
    void deleteUser(User user) {
      System.out.println("사용자 삭제: " + user.name);
    }
  }
}
