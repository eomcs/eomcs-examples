package com.eomcs.cleancode.ch05.exam01;

// 예제 4-2: 수직 거리 - 호출하는 함수와 호출되는 함수는 가까이 둔다 (Vertical Distance - Caller/Callee)
public class BadAndGood5 {

  private BadAndGood5() {}

  static class User {
    String getEmail() { return "user@example.com"; }
  }

  static class UserRepository {
    void save(User u) { System.out.println("DB 저장: " + u.getEmail()); }
  }

  static class EmailService {
    void send(String email, String msg) { System.out.println("이메일 전송 to " + email + ": " + msg); }
  }

  // Bad: 함수 정의 순서가 호출 순서와 반대다.
  // - register()는 validate() → save() → sendWelcomeEmail() 순으로 호출한다.
  // - 하지만 파일에서는 sendWelcomeEmail() → save() → validate() 순으로 정의되어 있다.
  // - 읽는 사람이 흐름을 따라가려면 파일을 위아래로 이동해야 한다.
  static class BadUserService {
    UserRepository userRepository = new UserRepository();
    EmailService emailService = new EmailService();

    public void register(User user) {
      validate(user);
      save(user);
      sendWelcomeEmail(user);
    }

    private void sendWelcomeEmail(User user) {
      emailService.send(user.getEmail(), "Welcome");
    }

    private void save(User user) {
      userRepository.save(user);
    }

    private void validate(User user) {
      if (user.getEmail() == null) {
        throw new IllegalArgumentException();
      }
    }
  }

  // Good: 함수 정의 순서가 호출 순서와 일치한다.
  // - register()가 호출하는 순서대로 validate() → save() → sendWelcomeEmail()이 바로 이어서 나온다.
  // - 위에서 아래로 자연스럽게 읽히는 흐름이 만들어진다.
  static class GoodUserService {
    UserRepository userRepository = new UserRepository();
    EmailService emailService = new EmailService();

    public void register(User user) {
      validate(user);
      save(user);
      sendWelcomeEmail(user);
    }

    private void validate(User user) {
      if (user.getEmail() == null) {
        throw new IllegalArgumentException();
      }
    }

    private void save(User user) {
      userRepository.save(user);
    }

    private void sendWelcomeEmail(User user) {
      emailService.send(user.getEmail(), "Welcome");
    }
  }
}
