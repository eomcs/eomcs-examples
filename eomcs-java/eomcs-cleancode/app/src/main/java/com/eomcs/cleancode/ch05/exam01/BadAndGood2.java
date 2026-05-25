package com.eomcs.cleancode.ch05.exam01;

// 예제 2: 개념은 빈 행으로 분리하라 (Vertical Openness Between Concepts)
public class BadAndGood2 {

  private BadAndGood2() {}

  static class User {
    String getEmail() { return "user@example.com"; }
  }

  static class UserRepository {
    void save(User u) { System.out.println("DB 저장: " + u.getEmail()); }
  }

  static class EmailService {
    void sendWelcomeEmail(User u) { System.out.println("환영 이메일 전송: " + u.getEmail()); }
  }

  // Bad: 필드와 메서드가 빈 줄 없이 붙어 있다.
  // - 메서드 간 경계가 보이지 않아 한 덩어리처럼 읽힌다.
  // - 어디서 새로운 개념이 시작하는지 파악하기 어렵다.
  static class BadUserService {
    private UserRepository userRepository = new UserRepository();
    private EmailService emailService = new EmailService();
    public void register(User user) {
      validate(user);
      userRepository.save(user);
      emailService.sendWelcomeEmail(user);
    }
    private void validate(User user) {
      if (user.getEmail() == null) {
        throw new IllegalArgumentException();
      }
    }
  }

  // Good: 필드 블록, 메서드 블록이 빈 줄로 분리된다.
  // - 메서드 단위가 눈에 들어온다.
  // - 코드의 논리적 단락이 명확해져 읽는 속도가 빨라진다.
  static class GoodUserService {

    private UserRepository userRepository = new UserRepository();
    private EmailService emailService = new EmailService();

    public void register(User user) {
      validate(user);
      userRepository.save(user);
      emailService.sendWelcomeEmail(user);
    }

    private void validate(User user) {
      if (user.getEmail() == null) {
        throw new IllegalArgumentException();
      }
    }
  }
}
