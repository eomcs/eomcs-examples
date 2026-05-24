package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood12 {

  static class User {
    private String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: 주석 처리한 코드 (Commented-Out Code)
  // - 왜 남아 있는지 이유를 알 수 없다.
  // - 이유가 있어 남겨놓았으리라 생각하여 다른 사람이 지우기를 주저한다.
  // - 시간이 지나면 쓸모없는 쓰레기가 된다.
  static class BadUserService {
    void process(User user) {
      save(user);
      // sendEmail(user);
      // updateCache(user);
    }

    private void save(User user) { System.out.println("저장: " + user.getName()); }
    // private void sendEmail(User user) { ... }
    // private void updateCache(User user) { ... }
  }

  // Good: 주석 처리한 코드는 그냥 삭제한다.
  // - 소스 코드 관리 시스템(Git)이 모든 이력을 기억한다.
  // - 필요하다면 언제든지 과거 커밋에서 코드를 찾아볼 수 있다.
  static class GoodUserService {
    void process(User user) {
      save(user);
    }

    private void save(User user) { System.out.println("저장: " + user.getName()); }
  }
}
