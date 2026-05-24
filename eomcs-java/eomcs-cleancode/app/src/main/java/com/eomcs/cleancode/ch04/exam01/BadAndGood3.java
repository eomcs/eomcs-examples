package com.eomcs.cleancode.ch04.exam01;

public class BadAndGood3 {

  static class User {
    private int status;
    User(int status) { this.status = status; }
    int getStatus() { return status; }
    boolean isActive() { return status == 1; }
  }

  static class Database {
    void save(User user) { System.out.println("DB 저장"); }
  }

  // Bad
  // - process()라는 이름은 아무것도 설명하지 않는다.
  // - 주석 "This method handles user processing"도 마찬가지로 모호하다.
  // - 조건 user.getStatus() == 1의 의미를 코드만으로 알 수 없다.
  // - 주석은 문제를 가릴 뿐, 나쁜 코드를 해결하지 못한다.
  static class BadUserProcessor {
    Database database = new Database();

    // This method handles user processing
    void process(User user) {
      if (user != null) {
        if (user.getStatus() == 1) {
          database.save(user);
        }
      }
    }
  }

  // Good: 함수 이름 개선과 조건 추출로 주석을 제거한다.
  // - saveActiveUser()라는 이름이 함수의 역할을 정확히 설명한다.
  // - isActiveUser()가 null 검사와 활성 상태 검사를 하나의 의미로 묶는다.
  // - 주석 없이도 코드를 읽는 순간 의도를 파악할 수 있다.
  static class GoodUserProcessor {
    Database database = new Database();

    void saveActiveUser(User user) {
      if (isActiveUser(user)) {
        database.save(user);
      }
    }

    private boolean isActiveUser(User user) {
      return user != null && user.isActive();
    }
  }
}
