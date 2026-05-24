package com.eomcs.cleancode.ch03.exam05;

public class BadAndGood4 {

  static class User {
    private int age;
    private int status;
    private double balance;
    User(int age, int status, double balance) {
      this.age = age; this.status = status; this.balance = balance;
    }
    int getAge() { return age; }
    int getStatus() { return status; }
    double getBalance() { return balance; }
  }

  // Bad
  // - 복잡한 조건이 if 블록에 인라인으로 나열되어 있다.
  // - age > 18, status == 1, balance > 1000 각각이 무엇을 의미하는지 한눈에 파악하기 어렵다.
  static class BadDiscountService {
    void applyDiscount(User user) {
      if (user.getAge() > 18 && user.getStatus() == 1 && user.getBalance() > 1000) {
        System.out.println("프리미엄 할인 적용");
      }
    }
  }

  // Good
  // - 복잡한 조건을 isEligibleForPremiumDiscount()로 추출한다.
  // - if 블록이 자연어처럼 읽히고, 조건의 의미가 함수 이름으로 표현된다.
  static class GoodDiscountService {
    void applyDiscount(User user) {
      if (isEligibleForPremiumDiscount(user)) {
        System.out.println("프리미엄 할인 적용");
      }
    }

    private boolean isEligibleForPremiumDiscount(User user) {
      return user.getAge() > 18 && user.getStatus() == 1 && user.getBalance() > 1000;
    }
  }
}
