package com.eomcs.cleancode.ch04.exam01;

public class BadAndGood1 {

  static class User {
    private int age;
    private int status;
    private int balance;

    User(int age, int status, int balance) {
      this.age = age; this.status = status; this.balance = balance;
    }

    int getAge() { return age; }
    int getStatus() { return status; }
    boolean isActive() { return status == 1; }
    int getBalance() { return balance; }
  }

  // Bad
  // - 조건이 복잡해서 주석 없이는 의미를 알 수 없다.
  // - 주석은 문제를 해결하지 않는다. 코드 자체가 여전히 이해하기 어렵다.
  // - 주석이 사라지면 읽는 사람은 조건식을 처음부터 해독해야 한다.
  static class BadDiscountService {
    void applyDiscount(User user) { System.out.println("할인 적용"); }

    void processDiscount(User user) {
      // Check if user is eligible for discount
      if (user.getAge() > 18 &&
          user.getStatus() == 1 &&
          user.getBalance() > 1000) {
        applyDiscount(user);
      }
    }
  }

  // Good: 조건을 의미 있는 이름의 메서드로 추출한다.
  // - isEligibleForDiscount()라는 이름만으로 조건의 목적을 즉시 이해할 수 있다.
  // - 주석이 없어도 코드 자체가 의미를 설명한다.
  // - 할인 자격 기준이 바뀌어도 isEligibleForDiscount() 한 곳만 수정하면 된다.
  static class GoodDiscountService {
    void applyDiscount(User user) { System.out.println("할인 적용"); }

    void processDiscount(User user) {
      if (isEligibleForDiscount(user)) {
        applyDiscount(user);
      }
    }

    private boolean isEligibleForDiscount(User user) {
      return user.getAge() > 18 &&
             user.isActive() &&
             user.getBalance() > 1000;
    }
  }
}
