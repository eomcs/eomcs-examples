package com.eomcs.cleancode.ch03.exam01;

public class BadAndGood1 {

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
    private String body;
    void setTo(String to) { this.to = to; }
    void setSubject(String subject) { this.subject = subject; }
    void setBody(String body) { this.body = body; }
  }

  static class Database {
    void save(User user) { System.out.println("DB 저장: " + user.getName()); }
  }

  static class EmailService {
    void send(Email email) {
      System.out.println("이메일 발송 → " + email.to + " / 제목: " + email.subject + " / 본문: " + email.body);
    }
  }

  // Bad
  // - 하나의 함수에 검증, 저장, 이메일 전송, 로깅 등 여러 책임이 섞여 있다.
  // - 읽기 어렵고 재사용·테스트가 어려우며, 변경 시 영향 범위가 크다.
  static class BadUserProcessor {
    Database database = new Database();
    EmailService emailService = new EmailService();

    void processUser(User user) {
      // 1. 유효성 검사
      if (user.getName() == null || user.getName().isEmpty()) {
        throw new IllegalArgumentException("Invalid name");
      }

      // 2. 사용자 저장
      database.save(user);

      // 3. 이메일 전송
      Email email = new Email();
      email.setTo(user.getEmail());
      email.setSubject("Welcome");
      email.setBody("Hello " + user.getName());
      emailService.send(email);

      // 4. 로그 기록
      System.out.println("User processed: " + user.getName());
    }
  }

  // Good
  // - processUser()는 전체 흐름만 담고, 세부 구현은 작은 함수로 위임한다.
  // - 각 함수는 하나의 일만 수행하고, 이름만으로 역할을 즉시 이해할 수 있다.
  static class GoodUserProcessor {
    Database database = new Database();
    EmailService emailService = new EmailService();

    void processUser(User user) {
      validateUser(user);
      saveUser(user);
      sendWelcomeEmail(user);
      logUserProcessing(user);
    }

    private void validateUser(User user) {
      if (user.getName() == null || user.getName().isEmpty()) {
        throw new IllegalArgumentException("Invalid name");
      }
    }

    private void saveUser(User user) {
      database.save(user);
    }

    private void sendWelcomeEmail(User user) {
      Email email = createWelcomeEmail(user);
      emailService.send(email);
    }

    private Email createWelcomeEmail(User user) {
      Email email = new Email();
      email.setTo(user.getEmail());
      email.setSubject("Welcome");
      email.setBody("Hello " + user.getName());
      return email;
    }

    private void logUserProcessing(User user) {
      System.out.println("User processed: " + user.getName());
    }
  }
}
