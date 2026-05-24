package com.eomcs.cleancode.ch03.exam09;

public class BadAndGood4 {

  static class User {
    private String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  static class DatabaseException extends RuntimeException {
    DatabaseException(String msg) { super(msg); }
  }

  static class Logger {
    void error(String msg) { System.out.println("[ERROR] " + msg); }
  }

  // Bad
  // - saveUser()가 검증, 저장, 오류 처리, 이메일 전송 등 여러 일을 한다.
  // - try/catch 앞뒤로 정상 로직이 섞여 있어 함수의 흐름이 불명확하다.
  // - 오류 처리가 함수 중간에 끼어 있어 "오류 처리는 하나의 일" 원칙을 위반한다.
  static class BadUserService {
    Logger logger = new Logger();

    void validate(User user) { System.out.println("검증: " + user.getName()); }
    void save(User user) { System.out.println("저장: " + user.getName()); }
    void sendWelcomeEmail(User user) { System.out.println("이메일 전송: " + user.getName()); }

    void saveUser(User user) {
      validate(user); // 정상 로직

      try {
        save(user);
      } catch (DatabaseException e) {
        logger.error(e.getMessage()); // 오류 처리가 중간에 끼어 있다
      }

      sendWelcomeEmail(user); // 정상 로직
    }
  }

  // Good: 오류 처리는 하나의 책임으로 분리한다.
  // - registerUser() → 전체 흐름을 표현하며, try가 함수 시작에서 catch/finally로 끝난다.
  // - saveValidUser() → 검증 후 저장만 담당한다. 오류 처리 코드가 없다.
  // - logDatabaseError() → 오류 기록만 담당한다.
  static class GoodUserService {
    Logger logger = new Logger();

    void validate(User user) { System.out.println("검증: " + user.getName()); }
    void save(User user) { System.out.println("저장: " + user.getName()); }
    void sendWelcomeEmail(User user) { System.out.println("이메일 전송: " + user.getName()); }

    void registerUser(User user) {
      try {
        saveValidUser(user);
        sendWelcomeEmail(user);
      } catch (DatabaseException e) {
        logDatabaseError(e);
      }
    }

    private void saveValidUser(User user) {
      validate(user);
      save(user);
    }

    private void logDatabaseError(DatabaseException e) {
      logger.error(e.getMessage());
    }
  }
}
