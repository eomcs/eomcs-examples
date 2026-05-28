package com.eomcs.cleancode.ch11.exam01;

// 예제 4: 의존성 주입 - 의존 객체는 생성자로 주입받아라
public class BadAndGood4 {

  private BadAndGood4() {}

  static class User {

    private final String email;

    User(String email) {
      this.email = email;
    }

    String email() { return email; }
  }

  interface EmailClient {
    void send(String email, String message);
  }

  static class SmtpEmailClient implements EmailClient {

    @Override
    public void send(String email, String message) {
      System.out.println("SMTP send: " + email);
    }
  }

  // Bad: EmailNotificationService가 SmtpEmailClient를 직접 생성한다
  //   - SMTP 구현에 강하게 묶인다
  //   - 테스트에서 실제 메일 발송을 막기 어렵다
  //   - 다른 메일 서비스로 교체하기 어렵다
  static class BadEmailNotificationService {

    private final EmailClient emailClient = new SmtpEmailClient(); // 직접 생성

    public void sendWelcomeEmail(User user) {
      emailClient.send(user.email(), "Welcome!");
    }
  }

  // Good: 서비스는 EmailClient를 사용만 한다
  //   - 실제 구현은 외부에서 주입한다
  //   - 테스트에서는 가짜 구현을 주입한다
  //   - 생성과 사용이 분리된다
  //   - 클래스의 책임이 명확해진다
  static class EmailNotificationService {

    private final EmailClient emailClient;

    EmailNotificationService(EmailClient emailClient) {
      this.emailClient = emailClient;
    }

    public void sendWelcomeEmail(User user) {
      emailClient.send(user.email(), "Welcome!");
    }
  }

  // 테스트용 구현: 실제 메일을 발송하지 않고 호출 여부만 기록한다
  static class FakeEmailClient implements EmailClient {

    boolean sent = false;

    @Override
    public void send(String email, String message) {
      sent = true;
    }
  }

  // main 또는 설정 모듈: 객체 생성과 조립을 담당한다
  static class AppMain {

    static void start() {
      EmailClient emailClient = new SmtpEmailClient();
      EmailNotificationService notificationService =
          new EmailNotificationService(emailClient);

      notificationService.sendWelcomeEmail(new User("kim@example.com"));
    }
  }
}
