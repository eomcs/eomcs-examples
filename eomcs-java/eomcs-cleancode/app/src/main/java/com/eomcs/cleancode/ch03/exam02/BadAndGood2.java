package com.eomcs.cleancode.ch03.exam02;

public class BadAndGood2 {

  static class User {
    private String name;
    private boolean active;
    User(String name, boolean active) { this.name = name; this.active = active; }
    String getName() { return name; }
    boolean isActive() { return active; }
  }

  static class Database {
    void connect() { System.out.println("DB 연결"); }
    void save(User user) { System.out.println("사용자 저장: " + user.getName()); }
  }

  // Bad
  // - 추상화 레벨이 섞여 있다.
  //   - user.isActive()         → 비즈니스 로직 (고수준)
  //   - database.connect()      → 구현 디테일  (저수준)
  //   - database.save(user)     → 구현 디테일  (저수준)
  //   - System.out.println(...) → 구현 디테일  (저수준)
  // - 서로 다른 레벨이 한 함수에 섞이면 이해하기 어렵다.
  static class BadUserService {
    Database database = new Database();

    void processUser(User user) {
      if (user.isActive()) {
        database.connect();
        database.save(user);
        System.out.println(user.getName());
      }
    }
  }

  // Good
  // - processActiveUser()는 같은 수준의 추상화(고수준)만 포함한다.
  // - 저수준 구현(connect, save, println)은 하위 함수로 내려간다.
  static class GoodUserService {
    Database database = new Database();

    void processActiveUser(User user) {
      if (user.isActive()) {
        saveUser(user);
        logUser(user);
      }
    }

    private void saveUser(User user) {
      database.connect();
      database.save(user);
    }

    private void logUser(User user) {
      System.out.println(user.getName());
    }
  }
}
