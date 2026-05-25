package com.eomcs.cleancode.ch05.exam01;

// 예제 4-3: 수직 거리 - 서로 관련된 개념은 물리적으로 가까이 배치한다 (Vertical Distance - Related Concepts)
public class BadAndGood6 {

  private BadAndGood6() {}

  static class User {
    String getEmail() { return "user@example.com"; }
  }

  static class UserRepository {
    void save(User u) { System.out.println("DB 저장: " + u.getEmail()); }
  }

  // Bad: 의미적으로 강하게 연관된 validate()와 isValidEmail()이 떨어져 있다.
  // - validate()가 isValidEmail()을 호출하지만 사이에 save()가 끼어 있다.
  // - 읽는 순서: validate() → 아래로 이동 → 다시 위로 이동 → isValidEmail()
  // - 인지 비용이 불필요하게 증가한다.
  static class BadUserService {
    UserRepository userRepository = new UserRepository();

    public void register(User user) {
      validate(user);
      save(user);
    }

    private boolean isValidEmail(String email) {
      return email != null && email.contains("@");
    }

    private void save(User user) {
      userRepository.save(user);
    }

    private void validate(User user) {
      if (!isValidEmail(user.getEmail())) {
        throw new IllegalArgumentException("Invalid email");
      }
    }
  }

  // Good: validate()와 isValidEmail()을 붙여서 배치한다.
  // - 관련 개념이 하나의 덩어리로 묶인다.
  // - 읽는 순서: validate() → isValidEmail() → 이해 완료
  // - 호출 관계가 없어도 의미적으로 연관된 코드는 가까이 두는 것이 좋다.
  static class GoodUserService {
    UserRepository userRepository = new UserRepository();

    public void register(User user) {
      validate(user);
      save(user);
    }

    private void validate(User user) {
      if (!isValidEmail(user.getEmail())) {
        throw new IllegalArgumentException("Invalid email");
      }
    }

    private boolean isValidEmail(String email) {
      return email != null && email.contains("@");
    }

    private void save(User user) {
      userRepository.save(user);
    }
  }
}
