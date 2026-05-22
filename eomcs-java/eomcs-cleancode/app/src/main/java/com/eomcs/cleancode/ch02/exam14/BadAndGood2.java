package com.eomcs.cleancode.ch02.exam14;

public class BadAndGood2 {

  static class Data {}

  static class Account {
    String id;
    double balance;
  }

  // Bad
  // - AccountManager → 역할이 모호하다.
  // - Data → 의미가 없다.
  // - update → 어떤 비즈니스 동작인지 불명확하다.
  static class BadAccountManager {
    void update(Data data) {
      System.out.println("데이터 업데이트");
    }
  }

  // Good
  // - AccountService → 계좌 관련 서비스임이 명확하다.
  // - updateAccountBalance → 실제 업무(잔액 변경)를 그대로 표현한다.
  static class GoodAccountService {
    void updateAccountBalance(Account account) {
      System.out.println("계좌 잔액 변경: " + account.id + " → " + account.balance);
    }
  }
}
