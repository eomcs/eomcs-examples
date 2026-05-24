package com.eomcs.cleancode.ch03.exam05;

public class BadAndGood1 {

  // Bad
  // - process → 무엇을 하는지 전혀 알 수 없다.
  // - doStuff → 완전히 의미 없는 이름.
  // - status == 1 → 어떤 상태인지 코드를 전부 읽어야만 이해할 수 있다.
  static class BadUserService {
    static class User {
      private int status;
      private String email;
      User(int status, String email) { this.status = status; this.email = email; }
      int getStatus() { return status; }
      String getEmail() { return email; }
    }

    void process(User user) {
      if (user.getStatus() == 1) {
        doStuff(user);
      }
    }
    private void doStuff(User user) {
      System.out.println("이메일 발송: " + user.getEmail());
    }
  }

  // Good
  // - activateUserIfPending → 조건(Pending)과 행동(Activate)이 이름에 모두 포함된다.
  // - isPendingActivation → 상태 의미가 명확하다.
  // - sendActivationEmail → 수행하는 작업이 명확하다.
  // - 코드가 자연어처럼 읽힌다.
  static class GoodUserService {
    static class User {
      private boolean pendingActivation;
      private String email;
      User(boolean pendingActivation, String email) {
        this.pendingActivation = pendingActivation; this.email = email;
      }
      boolean isPendingActivation() { return pendingActivation; }
      String getEmail() { return email; }
    }

    void activateUserIfPending(User user) {
      if (user.isPendingActivation()) {
        sendActivationEmail(user);
      }
    }
    private void sendActivationEmail(User user) {
      System.out.println("활성화 이메일 발송: " + user.getEmail());
    }
  }
}
