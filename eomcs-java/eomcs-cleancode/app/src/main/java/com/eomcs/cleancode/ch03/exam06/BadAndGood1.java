package com.eomcs.cleancode.ch03.exam06;

public class BadAndGood1 {

  static class User {
    String name;
    int age;
    String role;
    boolean active;
  }

  // Bad
  // - 인자가 4개 → 호출 시 순서를 기억해야 하고, 각 인자의 의미를 파악하기 어렵다.
  // - 새로운 속성 추가 시 호출부를 모두 변경해야 한다.
  static class BadUserService {
    void createUser(String name, int age, String role, boolean active) {
      System.out.println("사용자 생성: " + name + ", " + age + ", " + role + ", " + active);
    }
  }

  // Good
  // - 관련 데이터를 객체로 묶어 전달 → 의미가 명확하고 확장도 용이하다.
  // - 새 속성이 추가되어도 메서드 시그니처를 변경할 필요가 없다.
  static class GoodUserService {
    void createUser(User user) {
      System.out.println("사용자 생성: " + user.name + ", " + user.age + ", " + user.role + ", " + user.active);
    }
  }
}
