package com.eomcs.cleancode.ch07.exam07;

// 예제 2: null을 반환하지 마라 - findUser (예외를 던져라)
public class BadAndGood2 {

  private BadAndGood2() {}

  static class User {
    private Long id;
    private String name;
    User(Long id, String name) { this.id = id; this.name = name; }
    Long getId() { return id; }
    String getName() { return name; }
  }

  interface UserRepository {
    User findById(Long id); // 없으면 null
  }

  // Bad: 사용자가 없을 때 null을 반환한다.
  // - 호출자가 매번 null 체크를 해야 한다.
  // - null 체크를 빠뜨리면 NPE가 발생한다.
  // - 사용자가 없는 이유를 알 수 없다.
  static class BadUserService {
    private UserRepository userRepository;
    BadUserService(UserRepository repo) { this.userRepository = repo; }

    public User findUser(Long id) {
      return userRepository.findById(id); // 없으면 null 반환
    }
  }

  static class BadUserClient {
    void run(BadUserService userService) {
      User user = userService.findUser(1L);

      // null 체크 없이 사용하면 NPE 발생
      if (user != null) {
        System.out.println(user.getName());
      }
    }
  }

  // -----------------------------------------------------------------------

  // Good: 사용자가 없을 때 의미 있는 예외를 던진다.
  // - 실패 원인이 명확하다.
  // - 호출자가 의도적으로 처리할 수 있다.
  // - null 체크가 필요 없다.
  static class UserNotFoundException extends RuntimeException {
    UserNotFoundException(String message) { super(message); }
  }

  // Good: null 대신 UserNotFoundException을 던진다.
  // - "사용자 없음"의 의미가 예외 이름으로 명확하게 드러난다.
  // - 호출자는 catch로 의도적으로 처리한다.
  static class GoodUserService {
    private UserRepository userRepository;
    GoodUserService(UserRepository repo) { this.userRepository = repo; }

    public User findUser(Long id) {
      User user = userRepository.findById(id);

      if (user == null) {
        throw new UserNotFoundException("사용자 없음. id=" + id);
      }

      return user;
    }
  }

  static class GoodUserClient {
    void run(GoodUserService userService) {
      try {
        User user = userService.findUser(1L);
        System.out.println(user.getName()); // null 체크 없이 바로 사용한다
      } catch (UserNotFoundException e) {
        System.out.println("사용자 없음");
      }
    }
  }
}
