package com.eomcs.cleancode.ch07.exam01;

// 예제 3: 오류 코드보다 예외를 사용하라 - register
public class BadAndGood3 {

  private BadAndGood3() {}

  static class User {
    private String email;
    User(String email) { this.email = email; }
    String getEmail() { return email; }
  }

  // Bad: ErrorCode enum으로 오류를 반환한다.
  // - 호출자가 ErrorCode에 강하게 의존한다.
  // - 조건 분기가 계속 증가한다.
  // - 호출 코드가 점점 비대해진다.
  // - 내부 정책(INVALID_USER, DUPLICATED_EMAIL 등)이 외부로 노출된다.
  enum ErrorCode { OK, INVALID_USER, DUPLICATED_EMAIL }

  interface BadUserRepository {
    boolean existsByEmail(String email);
  }

  static class BadUserService {
    private BadUserRepository userRepository;

    BadUserService(BadUserRepository userRepository) {
      this.userRepository = userRepository;
    }

    public ErrorCode register(User user) {
      if (user == null) {
        return ErrorCode.INVALID_USER;
      }
      if (userRepository.existsByEmail(user.getEmail())) {
        return ErrorCode.DUPLICATED_EMAIL;
      }
      return ErrorCode.OK;
    }
  }

  static class BadUserClient {
    void run(BadUserService userService, User user) {
      ErrorCode result = userService.register(user);

      if (result == ErrorCode.OK) {
        System.out.println("회원가입 성공");
      } else if (result == ErrorCode.INVALID_USER) {
        System.out.println("잘못된 사용자");
      } else if (result == ErrorCode.DUPLICATED_EMAIL) {
        System.out.println("중복 이메일");
      }
    }
  }

  // -----------------------------------------------------------------------

  static class InvalidUserException extends RuntimeException {
    InvalidUserException() { super("유효하지 않은 사용자입니다."); }
  }

  static class DuplicatedEmailException extends RuntimeException {
    DuplicatedEmailException() { super("이미 사용 중인 이메일입니다."); }
  }

  interface GoodUserRepository {
    boolean existsByEmail(String email);
    void save(User user);
  }

  // Good: 예외를 던진다.
  // - 오류 처리 책임이 명확히 분리된다.
  // - 예외 단위로 확장 가능하다.
  // - 내부 로직이 외부에 노출되지 않는다.
  // - 검증 책임을 각 메서드로 분리해 단일 책임을 지킨다.
  static class GoodUserService {
    private GoodUserRepository userRepository;

    GoodUserService(GoodUserRepository userRepository) {
      this.userRepository = userRepository;
    }

    public void register(User user) {
      validateUser(user);
      validateEmail(user);
      userRepository.save(user);
    }

    private void validateUser(User user) {
      if (user == null) {
        throw new InvalidUserException();
      }
    }

    private void validateEmail(User user) {
      if (userRepository.existsByEmail(user.getEmail())) {
        throw new DuplicatedEmailException();
      }
    }
  }

  static class GoodUserClient {
    void run(GoodUserService userService, User user) {
      try {
        userService.register(user);
        System.out.println("회원가입 성공");
      } catch (InvalidUserException e) {
        System.out.println("잘못된 사용자");
      } catch (DuplicatedEmailException e) {
        System.out.println("중복 이메일");
      }
    }
  }
}
