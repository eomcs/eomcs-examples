package com.eomcs.cleancode.ch04.exam02;

public class BadAndGood3 {

  static class User {
    private int status;
    private int balance;

    User(int status, int balance) { this.status = status; this.balance = balance; }

    int getStatus() { return status; }
    int getBalance() { return balance; }
    boolean isActive() { return status == 1; }
    boolean hasSufficientBalance() { return balance > 1000; }
  }

  // Bad
  // - status == 1의 의미(활성 상태)를 코드만으로 알 수 없다.
  // - balance > 1000이 "충분한 잔고"를 의미한다는 사실이 코드에 드러나지 않는다.
  // - 조건을 해석하려면 주석에 의존해야 한다.
  static class BadDiscountService {
    void applyDiscount(User user) { System.out.println("할인 적용: 잔고=" + user.getBalance()); }

    void processDiscount(User user) {
      // if user is active and has enough balance
      if (user.getStatus() == 1 && user.getBalance() > 1000) {
        applyDiscount(user);
      }
    }
  }

  // Good: 복잡한 조건을 의미 있는 메서드로 추출한다.
  // - isEligibleForDiscount()가 조건의 비즈니스 의미를 이름으로 표현한다.
  // - isActive(), hasSufficientBalance() 내부 구현이 의미 단위로 분리된다.
  // - 조건이 바뀌어도 isEligibleForDiscount() 하나만 수정하면 된다.
  static class GoodDiscountService {
    void applyDiscount(User user) { System.out.println("할인 적용: 잔고=" + user.getBalance()); }

    void processDiscount(User user) {
      if (isEligibleForDiscount(user)) {
        applyDiscount(user);
      }
    }

    private boolean isEligibleForDiscount(User user) {
      return user.isActive() && user.hasSufficientBalance();
    }
  }
}
