package com.eomcs.cleancode.ch03.exam09;

public class BadAndGood5 {

  static class User {
    private String name;
    User(String name) { this.name = name; }
    String getName() { return name; }
  }

  // Bad: 오류 코드를 모아둔 enum이 의존성 자석이 된다.
  // - 모든 서비스 클래스가 ErrorCode enum에 의존한다.
  // - 새로운 오류가 생길 때마다 ErrorCode를 수정해야 하며, 그 변경이 모든 의존 클래스에 영향을 준다.
  // - 오류 코드가 재사용되어 의미가 흐려질 수 있다.
  enum ErrorCode {
    OK,
    INVALID_USER,
    USER_NOT_FOUND,
    DATABASE_ERROR,
    PERMISSION_DENIED
  }

  static class UserRepository {
    void save(User user) { System.out.println("저장: " + user.getName()); }
  }

  static class BadUserService {
    UserRepository userRepository = new UserRepository();

    ErrorCode saveUser(User user) {
      if (user == null) {
        return ErrorCode.INVALID_USER; // 모든 호출자가 ErrorCode에 의존
      }
      try {
        userRepository.save(user);
        return ErrorCode.OK;
      } catch (Exception e) {
        return ErrorCode.DATABASE_ERROR;
      }
    }
  }

  // Good: 예외 클래스를 분리하여 의존성을 분산한다.
  // - 오류의 의미가 예외 타입 이름으로 명확히 드러난다.
  // - 새로운 오류는 새 예외 클래스를 추가하는 방식으로 확장한다. 기존 코드를 수정하지 않는다.
  // - 특정 예외만 필요한 클래스는 관련 예외에만 의존하면 된다.
  static class InvalidUserException extends RuntimeException {
    InvalidUserException() { super("Invalid user"); }
  }

  static class UserSaveException extends RuntimeException {
    UserSaveException(Throwable cause) { super("Failed to save user", cause); }
  }

  static class GoodUserService {
    UserRepository userRepository = new UserRepository();

    void saveUser(User user) {
      if (user == null) {
        throw new InvalidUserException(); // 의미가 명확한 예외 타입
      }
      try {
        userRepository.save(user);
      } catch (Exception e) {
        throw new UserSaveException(e); // 오류별로 독립된 예외 클래스
      }
    }
  }

  static class GoodUsage {
    void demo(GoodUserService service, User user) {
      try {
        service.saveUser(user);
        System.out.println("저장 완료");
      } catch (InvalidUserException e) {
        System.out.println("[WARN] " + e.getMessage());
      } catch (UserSaveException e) {
        System.out.println("[ERROR] " + e.getMessage());
      }
    }
  }
}
