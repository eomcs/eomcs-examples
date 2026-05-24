package com.eomcs.cleancode.ch03.exam10;

public class BadAndGood1 {

  static class User {
    private String name;
    private String email;
    User(String name, String email) { this.name = name; this.email = email; }
    String getName() { return name; }
    String getEmail() { return email; }
  }

  static class UserRepository {
    void save(User user) { System.out.println("저장: " + user.getName()); }
    void update(User user) { System.out.println("수정: " + user.getName()); }
  }

  // Bad
  // - 이름과 이메일 검증 로직이 createUser()와 updateUser() 양쪽에 그대로 반복된다.
  // - 검증 규칙이 바뀌면 두 메서드를 모두 수정해야 한다.
  // - 한 곳을 수정하고 다른 곳을 놓치면 바로 버그가 된다.
  static class BadUserService {
    UserRepository userRepository = new UserRepository();

    void createUser(User user) {
      if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
      }
      if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
      }
      userRepository.save(user);
    }

    void updateUser(User user) {
      if (user.getName() == null || user.getName().isBlank()) { // 중복
        throw new IllegalArgumentException("Invalid name");
      }
      if (user.getEmail() == null || !user.getEmail().contains("@")) { // 중복
        throw new IllegalArgumentException("Invalid email");
      }
      userRepository.update(user);
    }
  }

  // Good: 중복된 검증 로직을 validateUser()로 추출한다.
  // - 검증 규칙 변경 시 validateUser() 하나만 수정하면 된다.
  // - createUser()와 updateUser()의 핵심 흐름이 명확하게 드러난다.
  // - 테스트하기 쉽고 재사용성이 높아진다.
  static class GoodUserService {
    UserRepository userRepository = new UserRepository();

    void createUser(User user) {
      validateUser(user);
      userRepository.save(user);
    }

    void updateUser(User user) {
      validateUser(user);
      userRepository.update(user);
    }

    private void validateUser(User user) {
      validateName(user);
      validateEmail(user);
    }

    private void validateName(User user) {
      if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
      }
    }

    private void validateEmail(User user) {
      if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
      }
    }
  }
}
