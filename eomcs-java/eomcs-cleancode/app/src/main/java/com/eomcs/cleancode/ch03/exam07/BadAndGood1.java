package com.eomcs.cleancode.ch03.exam07;

public class BadAndGood1 {

  static class User {
    private String name;
    private String password;
    User(String name, String password) { this.name = name; this.password = password; }
    String getName() { return name; }
    String getPassword() { return password; }
  }

  static class Session {
    private static boolean initialized = false;
    static void initialize() { initialized = true; System.out.println("세션 초기화"); }
    static boolean isInitialized() { return initialized; }
  }

  static class UserRepository {
    private User storedUser;
    UserRepository(User user) { this.storedUser = user; }
    User findByName(String name) {
      return storedUser.getName().equals(name) ? storedUser : null;
    }
  }

  // Bad
  // - checkPassword → 비밀번호 확인만 할 것 같지만 실제로는 Session.initialize()까지 수행한다.
  // - 함수 이름이 숨겨진 동작을 드러내지 않아 호출자가 예측할 수 없다.
  static class BadAuthService {
    UserRepository userRepository;
    BadAuthService(UserRepository repo) { this.userRepository = repo; }

    boolean checkPassword(String userName, String password) {
      User user = userRepository.findByName(userName);
      if (user != null && user.getPassword().equals(password)) {
        Session.initialize(); // 숨겨진 부작용
        return true;
      }
      return false;
    }
  }

  // Good
  // - isPasswordValid → 검증만 수행한다. 이름과 동작이 일치한다.
  // - initializeSession → 세션 초기화를 별도 함수로 명확히 분리한다.
  // - 호출부에서 모든 동작이 명확하게 드러난다.
  static class GoodAuthService {
    UserRepository userRepository;
    GoodAuthService(UserRepository repo) { this.userRepository = repo; }

    boolean isPasswordValid(String userName, String password) {
      User user = userRepository.findByName(userName);
      return user != null && user.getPassword().equals(password);
    }

    void initializeSession(User user) {
      Session.initialize();
    }
  }

  static class GoodUsage {
    void demo(GoodAuthService auth, User user) {
      if (auth.isPasswordValid(user.getName(), user.getPassword())) {
        auth.initializeSession(user); // 두 동작이 모두 명확하게 드러난다.
      }
    }
  }
}
