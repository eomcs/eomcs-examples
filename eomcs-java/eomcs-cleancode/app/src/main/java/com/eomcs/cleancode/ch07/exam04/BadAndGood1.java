package com.eomcs.cleancode.ch07.exam04;

// 예제 1: 예외에 의미를 제공하라 - findUser
public class BadAndGood1 {

  private BadAndGood1() {}

  static class Logger {
    void error(String message, Throwable e) {
      System.out.println("[ERROR] " + message + " | cause: " + e.getClass().getSimpleName());
    }
  }

  static class User {
    private Long id;
    private String name;
    User(Long id, String name) { this.id = id; this.name = name; }
    Long getId() { return id; }
    String getName() { return name; }
  }

  interface UserRepository {
    User findById(Long userId);
  }

  // Bad: 모호한 메시지로 예외를 던진다.
  // - "Not found"만 보고는 무엇을 찾지 못했는지 알기 어렵다.
  // - 어떤 userId에서 실패했는지 알 수 없다.
  // - 로그만 보고 원인을 추적하기 어렵다.
  static class BadUserService {
    private UserRepository userRepository;
    BadUserService(UserRepository repo) { this.userRepository = repo; }

    @SuppressWarnings("java:S112") // 의도적인 Bad 예제 — 모호한 RuntimeException이 핵심 문제점
    public User findUser(Long userId) {
      User user = userRepository.findById(userId);

      if (user == null) {
        throw new RuntimeException("Not found");
      }

      return user;
    }
  }

  static class BadUserClient {
    void run(BadUserService userService, Logger logger) {
      try {
        User user = userService.findUser(10L);
        System.out.println(user.getName());
      } catch (RuntimeException e) {
        logger.error(e.getMessage(), e); // 로그: "Not found" — 추적 불가
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 의미 있는 예외 타입과 충분한 컨텍스트 메시지를 제공한다.
  static class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String message) { super(message); }
  }

  // Good: 실패한 작업, 실패한 대상을 예외 메시지에 담는다.
  // - "사용자를 찾을 수 없습니다. userId=10" — 추적 가능
  // - 예외 타입만 봐도 원인이 명확하다.
  // - 디버깅할 때 호출자에게 다시 물어보지 않아도 된다.
  static class GoodUserService {
    private UserRepository userRepository;
    GoodUserService(UserRepository repo) { this.userRepository = repo; }

    public User findUser(Long userId) {
      User user = userRepository.findById(userId);

      if (user == null) {
        throw new UserNotFoundException(
            "사용자를 찾을 수 없습니다. userId=" + userId
        );
      }

      return user;
    }
  }

  static class GoodUserClient {
    void run(GoodUserService userService, Logger logger) {
      try {
        User user = userService.findUser(10L);
        System.out.println(user.getName());
      } catch (UserNotFoundException e) {
        logger.error(e.getMessage(), e); // 로그: "사용자를 찾을 수 없습니다. userId=10"
      }
    }
  }
}
