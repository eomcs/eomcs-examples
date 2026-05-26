package com.eomcs.cleancode.ch07.exam01;

// 예제 2: 오류 코드보다 예외를 사용하라 - withdraw
public class BadAndGood2 {

  private BadAndGood2() {}

  static class Account {
    private int balance;

    Account(int balance) { this.balance = balance; }

    int getBalance() { return balance; }
    void decreaseBalance(int amount) { balance -= amount; }
  }

  static class Receipt {
    void print() { System.out.println("영수증 출력"); }
  }

  // Bad: boolean으로 성공/실패만 반환한다.
  // - 실패 원인이 전혀 드러나지 않는다.
  // - 단순 성공/실패로만 표현되어 다양한 오류 상황을 확장하기 어렵다.
  // - 호출 코드에서 결과 값을 해석해야 한다.
  static class BadAccountService {
    public boolean withdraw(Account account, int amount) {
      if (account.getBalance() < amount) {
        return false;
      }
      account.decreaseBalance(amount);
      return true;
    }
  }

  static class BadAccountClient {
    void run(Account account, Receipt receipt) {
      BadAccountService service = new BadAccountService();

      if (service.withdraw(account, 10000)) {
        receipt.print();
      } else {
        System.out.println("실패");
      }
    }
  }

  // -----------------------------------------------------------------------

  static class InsufficientBalanceException extends RuntimeException {
    InsufficientBalanceException() { super("잔액이 부족합니다."); }
  }

  // Good: 예외를 던진다.
  // - 실패 원인이 예외 이름으로 명확하게 표현된다.
  // - 다양한 오류 상황을 예외 타입으로 확장할 수 있다.
  // - 정상 흐름이 중심이 된다.
  static class GoodAccountService {
    public void withdraw(Account account, int amount) {
      if (account.getBalance() < amount) {
        throw new InsufficientBalanceException();
      }
      account.decreaseBalance(amount);
    }
  }

  static class GoodAccountClient {
    void run(Account account, Receipt receipt) {
      GoodAccountService service = new GoodAccountService();

      try {
        service.withdraw(account, 10000);
        receipt.print();
      } catch (InsufficientBalanceException e) {
        System.out.println("잔액 부족");
      }
    }
  }
}
