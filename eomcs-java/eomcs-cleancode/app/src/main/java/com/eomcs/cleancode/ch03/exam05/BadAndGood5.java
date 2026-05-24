package com.eomcs.cleancode.ch03.exam05;

public class BadAndGood5 {

  static class User {
    private boolean active;
    private double balance;
    User(boolean active, double balance) { this.active = active; this.balance = balance; }
    boolean isActive() { return active; }
    double getBalance() { return balance; }
  }

  // Bad
  // - 주석으로 조건의 의미를 설명해야 한다.
  // - 주석이 필요하다는 것 자체가 코드에 문제가 있다는 신호다.
  static class BadDiscountService {
    void applyDiscount(User user) {
      // 사용자가 활성 상태이고 잔액이 충분하면 할인 적용
      if (user.isActive() && user.getBalance() > 1000) {
        System.out.println("할인 적용");
      }
    }
  }

  // Good
  // - 조건을 isEligibleForDiscount()로 추출해 주석 없이도 의도가 드러난다.
  // - 함수 이름이 주석을 대체한다.
  static class GoodDiscountService {
    void applyDiscount(User user) {
      if (isEligibleForDiscount(user)) {
        System.out.println("할인 적용");
      }
    }

    private boolean isEligibleForDiscount(User user) {
      return user.isActive() && user.getBalance() > 1000;
    }
  }
}
