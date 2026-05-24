package com.eomcs.cleancode.ch03.exam10;

public class BadAndGood2 {

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

  static class EmailService {
    void send(Email email) {
      System.out.println("수신: " + email.getTo() + " | 제목: " + email.getSubject() + " | 내용: " + email.getBody());
    }
  }

  // Bad
  // - 이메일 객체를 만드는 코드(setTo, setSubject, setBody + send)가 두 메서드에 반복된다.
  // - from, trackingId 같은 필드가 추가되면 두 곳을 모두 수정해야 한다.
  // - 이메일 전송 흐름이 여러 곳에 흩어져 있다.
  static class BadEmailNotificationService {
    EmailService emailService = new EmailService();

    void sendWelcomeEmail(User user) {
      Email email = new Email();
      email.setTo(user.getEmail());
      email.setSubject("Welcome");
      email.setBody("Hello " + user.getName());
      emailService.send(email); // 중복된 이메일 생성 및 전송 흐름
    }

    void sendPasswordResetEmail(User user) {
      Email email = new Email();
      email.setTo(user.getEmail());
      email.setSubject("Password Reset");
      email.setBody("Reset your password, " + user.getName());
      emailService.send(email); // 중복된 이메일 생성 및 전송 흐름
    }
  }

  // Good: 공통 이메일 생성 및 전송 로직을 추출한다.
  // - 이메일 생성 규칙이 createEmail() 한 곳에 모인다.
  // - 전송 방식 변경 시 sendEmail()만 수정하면 된다.
  // - 각 public 메서드는 제목과 본문 결정이라는 자신의 의도만 드러낸다.
  static class GoodEmailNotificationService {
    EmailService emailService = new EmailService();

    void sendWelcomeEmail(User user) {
      sendEmail(user, "Welcome", "Hello " + user.getName());
    }

    void sendPasswordResetEmail(User user) {
      sendEmail(user, "Password Reset", "Reset your password, " + user.getName());
    }

    private void sendEmail(User user, String subject, String body) {
      Email email = createEmail(user, subject, body);
      emailService.send(email);
    }

    private Email createEmail(User user, String subject, String body) {
      Email email = new Email();
      email.setTo(user.getEmail());
      email.setSubject(subject);
      email.setBody(body);
      return email;
    }
  }
}
