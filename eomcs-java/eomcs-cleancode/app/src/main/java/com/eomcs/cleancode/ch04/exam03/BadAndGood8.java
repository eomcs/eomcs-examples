package com.eomcs.cleancode.ch04.exam03;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BadAndGood8 {

  static class User {
    private final String email;
    User(String email) { this.email = email; }
    String getEmail() { return email; }
  }

  static class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String email) { super("User not found: " + email); }
  }

  static class UserRepository {
    private final Map<String, User> store = new HashMap<>();
    void save(User user) { store.put(user.getEmail(), user); }
    Optional<User> findByEmail(String email) { return Optional.ofNullable(store.get(email)); }
  }

  // Bad: public API에 Javadoc이 없다.
  // - 외부 호출자는 파라미터 의미, 반환값, 예외 발생 조건을 내부 구현을 열지 않고는 알 수 없다.
  // - 예외가 언제 던져지는지 모르면 호출자가 적절한 예외 처리를 하지 못한다.
  static class BadUserService {
    private UserRepository userRepository = new UserRepository();

    public User findByEmail(String email) {
      return userRepository.findByEmail(email)
          .orElseThrow(() -> new UserNotFoundException(email));
    }
  }

  // Good: 공개 API의 Javadoc (Javadocs in Public APIs)
  // - public API 사용자는 내부 구현을 보지 못하므로 문서가 반드시 필요하다.
  // - 파라미터·반환값·예외를 명확히 기술하여 호출자가 올바르게 사용할 수 있게 한다.
  // - 단, 내부 private 메서드에 Javadoc을 남발하면 오히려 잡음이 된다.
  static class GoodUserService {
    private UserRepository userRepository = new UserRepository();

    /**
     * 이메일로 사용자를 조회한다.
     *
     * @param email 조회할 사용자의 이메일 주소
     * @return 해당 이메일을 가진 사용자
     * @throws UserNotFoundException 해당 이메일을 가진 사용자가 없는 경우
     */
    public User findByEmail(String email) {
      return userRepository.findByEmail(email)
          .orElseThrow(() -> new UserNotFoundException(email));
    }
  }
}
