package com.eomcs.cleancode.ch10.exam02;

// 예제 1: 클래스는 작아야 한다 - UserManager vs UserRegistrationService
public class BadAndGood1 {

  private BadAndGood1() {}

  static class User {
    private final String email;

    User(String email) {
      this.email = email;
    }

    String email() { return email; }
  }

  // Bad: 검증·저장·이메일·감사로그를 한 클래스에서 모두 담당
  static class BadUserManager {

    public void register(User user) {
      validate(user);
      save(user);
      sendWelcomeEmail(user);
      writeAuditLog(user);
    }

    private void validate(User user) {
      if (user.email() == null) {
        throw new IllegalArgumentException("email required");
      }
    }

    private void save(User user) {
      System.out.println("save user");
    }

    private void sendWelcomeEmail(User user) {
      System.out.println("send email");
    }

    private void writeAuditLog(User user) {
      System.out.println("write audit log");
    }
  }

  // Good: 각 책임을 별도 클래스로 분리

  static class UserValidator {
    void validate(User user) {
      if (user.email() == null) {
        throw new IllegalArgumentException("email required");
      }
    }
  }

  interface UserRepository {
    void save(User user);
  }

  interface WelcomeEmailSender {
    void sendTo(User user);
  }

  interface AuditLogger {
    void logRegistration(User user);
  }

  static class FakeUserRepository implements UserRepository {
    private boolean saved = false;

    @Override
    public void save(User user) { saved = true; }

    boolean wasSaved() { return saved; }
  }

  static class FakeWelcomeEmailSender implements WelcomeEmailSender {
    private boolean sent = false;

    @Override
    public void sendTo(User user) { sent = true; }

    boolean wasSent() { return sent; }
  }

  static class FakeAuditLogger implements AuditLogger {
    private boolean logged = false;

    @Override
    public void logRegistration(User user) { logged = true; }

    boolean wasLogged() { return logged; }
  }

  static class UserRegistrationService {

    private final UserValidator validator;
    private final UserRepository repository;
    private final WelcomeEmailSender emailSender;
    private final AuditLogger auditLogger;

    UserRegistrationService(
        UserValidator validator,
        UserRepository repository,
        WelcomeEmailSender emailSender,
        AuditLogger auditLogger) {
      this.validator = validator;
      this.repository = repository;
      this.emailSender = emailSender;
      this.auditLogger = auditLogger;
    }

    void register(User user) {
      validator.validate(user);
      repository.save(user);
      emailSender.sendTo(user);
      auditLogger.logRegistration(user);
    }
  }
}
