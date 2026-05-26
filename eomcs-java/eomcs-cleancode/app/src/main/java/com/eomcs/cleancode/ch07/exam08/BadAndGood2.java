package com.eomcs.cleancode.ch07.exam08;

// 예제 2: null을 전달하지 마라 - sendEmail (묵살 vs 예외)
public class BadAndGood2 {

  private BadAndGood2() {}

  interface EmailClient {
    void send(String email);
  }

  // Bad: null이 전달되면 조용히 리턴한다.
  // - 이메일이 없는 상황이 조용히 무시된다.
  // - 중요한 오류를 놓칠 수 있다.
  // - 시스템 상태가 조용히 깨질 수 있다.
  static class BadEmailService {
    private EmailClient emailClient;
    BadEmailService(EmailClient client) { this.emailClient = client; }

    public void sendEmail(String email) {
      if (email == null) {
        return; // null을 묵살한다
      }

      emailClient.send(email);
    }
  }

  static class BadEmailClient {
    void run(BadEmailService emailService) {
      emailService.sendEmail(null); // null을 전달해도 오류가 드러나지 않는다
    }
  }

  // -----------------------------------------------------------------------

  // Good: null이 전달되면 즉시 예외를 던진다.
  // - 잘못된 입력이 즉시 드러난다.
  // - 오류를 조기에 발견할 수 있다.
  // - 의도하지 않은 동작이 방지된다.
  static class GoodEmailService {
    private EmailClient emailClient;
    GoodEmailService(EmailClient client) { this.emailClient = client; }

    public void sendEmail(String email) {
      if (email == null) {
        throw new IllegalArgumentException("email must not be null");
      }

      emailClient.send(email);
    }
  }

  static class User {
    private String email;
    User(String email) { this.email = email; }
    String getEmail() { return email; }
  }

  static class GoodEmailClient {
    void run(GoodEmailService emailService, User user) {
      emailService.sendEmail(user.getEmail()); // null이 아닌 실제 값을 전달한다
    }
  }
}
