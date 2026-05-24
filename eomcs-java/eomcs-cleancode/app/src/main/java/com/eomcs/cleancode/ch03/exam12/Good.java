package com.eomcs.cleancode.ch03.exam12;

public class Good {

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
    String getTo() { return to; }
    String getSubject() { return subject; }
    String getBody() { return body; }
  }

  static class UserRepository {
    void save(User user) { System.out.println("저장: " + user.getEmail()); }
  }

  static class EmailService {
    void send(Email email) {
      System.out.println("이메일 전송: " + email.getTo() + " / " + email.getSubject() + " / " + email.getBody());
    }
  }

  // Good: 리팩토링 후 — 각 단계를 별도 함수로 추출한다.
  // - register()는 전체 흐름(검증 → 저장 → 이메일 → 로그)만 보여준다.
  // - 읽는 사람은 세부 구현을 보지 않아도 흐름 전체를 이해할 수 있다.
  // - 각 함수가 하나의 역할만 담당하므로 수정과 테스트가 쉽다.
  // 👉 처음 동작하는 초안을 작성한 뒤 중복 제거 → 이름 개선 → 함수 분리 순으로 다듬는다.
  static class GoodUserRegistrationService {
    UserRepository userRepository = new UserRepository();
    EmailService emailService = new EmailService();

    void register(User user) {
      validateUser(user);
      saveUser(user);
      sendWelcomeEmail(user);
      logRegistration(user);
    }

    private void validateUser(User user) {
      validateEmail(user);
      validateName(user);
    }

    private void validateEmail(User user) {
      if (user.getEmail() == null || !user.getEmail().contains("@")) {
        throw new IllegalArgumentException("Invalid email");
      }
    }

    private void validateName(User user) {
      if (user.getName() == null || user.getName().isBlank()) {
        throw new IllegalArgumentException("Invalid name");
      }
    }

    private void saveUser(User user) {
      userRepository.save(user);
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

    private void logRegistration(User user) {
      System.out.println("User registered: " + user.getEmail());
    }
  }
}
