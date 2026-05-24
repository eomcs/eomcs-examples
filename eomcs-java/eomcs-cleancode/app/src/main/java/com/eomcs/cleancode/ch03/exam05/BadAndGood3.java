package com.eomcs.cleancode.ch03.exam05;

public class BadAndGood3 {

  static class User {
    private int age;
    private double balance;
    User(int age, double balance) { this.age = age; this.balance = balance; }
    int getAge() { return age; }
    double getBalance() { return balance; }
  }

  // Bad
  // - flag → 무엇을 의미하는지 전혀 알 수 없다.
  // - 변수 이름이 코드의 의도를 전달하지 못한다.
  static class BadDiscountService {
    void applyDiscount(User user) {
      boolean flag = user.getAge() >= 18 && user.getBalance() >= 1000;
      if (flag) {
        System.out.println("할인 적용");
      }
    }
  }

  // Good
  // - isUserEligibleForDiscount → 조건의 의미가 이름에 포함된다.
  // - 별도의 설명 없이도 변수의 의미를 즉시 이해할 수 있다.
  static class GoodDiscountService {
    void applyDiscount(User user) {
      boolean isUserEligibleForDiscount = user.getAge() >= 18 && user.getBalance() >= 1000;
      if (isUserEligibleForDiscount) {
        System.out.println("할인 적용");
      }
    }
  }
}
