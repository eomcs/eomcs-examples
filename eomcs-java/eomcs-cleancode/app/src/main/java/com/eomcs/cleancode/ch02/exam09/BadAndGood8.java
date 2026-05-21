package com.eomcs.cleancode.ch02.exam09;

public class BadAndGood8 {

  // Bad
  // - process → 의미 불명확. doStuff → 무엇인지 모름. flag → 값의 의미 불명확.
  // - 코드를 전부 읽어야 비로소 이 메서드가 무엇을 하는지 파악할 수 있다.
  static class BadUserService {
    static class User {
      int flag;
    }
    void process(User u) {
      if (u.flag == 1) {
        doStuff(u);
      }
    }
    void doStuff(User u) {
      System.out.println("처리 중...");
    }
  }

  // Good
  // - activateUser → 명확한 행동. isPendingActivation → 조건 의미 명확.
  // - sendActivationEmail → 수행 작업 명확. 이름만 읽어도 전체 흐름을 이해할 수 있다.
  static class GoodUserService {
    static class User {
      boolean pendingActivation;
      String email;
      boolean isPendingActivation() { return pendingActivation; }
    }
    void activateUser(User user) {
      if (user.isPendingActivation()) {
        sendActivationEmail(user);
      }
    }
    void sendActivationEmail(User user) {
      System.out.println("활성화 이메일 발송: " + user.email);
    }
  }
}
