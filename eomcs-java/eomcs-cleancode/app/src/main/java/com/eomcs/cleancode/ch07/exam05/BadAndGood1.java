package com.eomcs.cleancode.ch07.exam05;

import java.sql.SQLException;

// 예제 1: 호출자를 고려해 예외 클래스를 정의하라 - UserService.getUser
public class BadAndGood1 {

  private BadAndGood1() {}

  static class User {
    private Long id;
    User(Long id) { this.id = id; }
    Long getId() { return id; }
  }

  interface UserRepository {
    User findById(Long id) throws SQLException;
  }

  // Bad: 내부 기술 예외(SQLException)를 그대로 호출자에게 노출한다.
  // - 호출자가 DB 기술 세부사항(SQLException, errorCode)을 알아야 한다.
  // - 비즈니스 로직이 DB 구현에 묶인다.
  // - DB를 다른 기술로 바꾸면 호출자 코드도 함께 바꿔야 한다.
  static class BadUserService {
    private UserRepository userRepository;
    BadUserService(UserRepository repo) { this.userRepository = repo; }

    public User getUser(Long id) throws SQLException {
      try {
        return userRepository.findById(id);
      } catch (SQLException e) {
        throw e; // 저수준 예외를 그대로 던진다
      }
    }
  }

  static class BadUserClient {
    void run(BadUserService userService) {
      try {
        User user = userService.getUser(1L);
        System.out.println("사용자 조회: " + user.getId());
      } catch (SQLException e) {
        // 호출자가 DB 내부 오류 코드까지 알아야 한다
        if (e.getErrorCode() == 100) {
          System.out.println("사용자 없음");
        } else {
          System.out.println("DB 오류");
        }
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 호출자 관점의 도메인 예외로 변환한다.
  static class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String message, Throwable cause) { super(message, cause); }
  }

  // Good: 내부 SQLException을 UserNotFoundException으로 변환해 노출하지 않는다.
  // - 호출자는 DB를 몰라도 된다.
  // - 예외가 도메인 의미(사용자 조회 실패)를 가진다.
  // - DB를 다른 기술로 바꿔도 호출자 코드는 변경되지 않는다.
  static class GoodUserService {
    private UserRepository userRepository;
    GoodUserService(UserRepository repo) { this.userRepository = repo; }

    public User getUser(Long id) {
      try {
        return userRepository.findById(id);
      } catch (SQLException e) {
        throw new UserNotFoundException("사용자 조회 실패. id=" + id, e);
      }
    }
  }

  static class GoodUserClient {
    void run(GoodUserService userService) {
      try {
        User user = userService.getUser(1L);
        System.out.println("사용자 조회: " + user.getId());
      } catch (UserNotFoundException e) {
        // 호출자는 도메인 예외만 알면 된다
        System.out.println("사용자를 찾을 수 없음");
      }
    }
  }
}
