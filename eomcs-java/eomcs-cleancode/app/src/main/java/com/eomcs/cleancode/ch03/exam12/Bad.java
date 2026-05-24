package com.eomcs.cleancode.ch03.exam12;

public class Bad {

  static class User {
    private String name;
    private String email;
    User(String name, String email) { this.name = name; this.email = email; }
    String getName() { return name; }
    String getEmail() { return email; }
  }

  static class Email {
    private String to;
    private String subject;
    void setTo(String to) { this.to = to; }
    void setSubject(String subject) { this.subject = subject; }
    void setBody(String body) { System.out.println("본문: " + body); }
    String getTo() { return to; }
    String getSubject() { return subject; }
  }

  static class UserRepository {
    void save(User user) { System.out.println("저장: " + user.getEmail()); }
  }

  static class EmailService {
    void send(Email email) { System.out.println("이메일 전송: " + email.getTo() + " / " + email.getSubject()); }
  }

  // Bad: 함수 초기 버전 — 여러 일을 한 함수에서 처리한다.
  // - 사용자 검증, 저장, 이메일 생성, 이메일 전송, 로그 출력이 모두 register()에 뒤섞인다.
  // - 함수가 길어서 한 번에 파악하기 어렵다.
  // - 어느 한 단계를 수정하려면 함수 전체를 읽어야 한다.
  // - 각 단계를 독립적으로 테스트하기 어렵다.
  // 👉 처음 작성한 초안은 동작하지만 깨끗하지 않다.
  static class BadUserRegistrationService {
    UserRepository userRepository = new UserRepository();
    EmailService emailService = new EmailService();

    void register(User user) {
      if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
      }

      if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
      }

      userRepository.save(user);

      Email email = new Email();
      email.setTo(user.getEmail());
      email.setSubject("Welcome");
      email.setBody("Hello " + user.getName());

      emailService.send(email);

      System.out.println("User registered: " + user.getEmail());
    }
  }
}
