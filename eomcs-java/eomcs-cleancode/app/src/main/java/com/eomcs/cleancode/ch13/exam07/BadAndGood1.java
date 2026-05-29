package com.eomcs.cleancode.ch13.exam07;

// 예제 1: 임계 구역 식별 - 공유 상태를 변경하는 부분만 동기화하라
public class BadAndGood1 {

  private BadAndGood1() {}

  // Bad: 검증, 로그, 알림까지 모두 락 안에서 실행된다
  //   - balance -= amount만 실제 공유 상태 변경이다
  //   - 락을 오래 잡아 다른 스레드가 불필요하게 오래 기다린다
  //   - 경쟁(contention)이 증가한다
  static class BadAccount {

    private int balance = 0;

    public synchronized int getBalance() {
      return balance;
    }

    public synchronized void withdraw(int amount) {
      validate(amount);          // 공유 상태와 무관, 락 불필요
      writeAuditLog(amount);     // I/O 작업, 락 불필요
      sendNotification(amount);  // 외부 호출, 락 불필요

      balance -= amount;         // 실제 임계 구역
    }

    private void validate(int amount) {
      if (amount <= 0) {
        throw new IllegalArgumentException("amount must be positive");
      }
    }

    private void writeAuditLog(int amount) {
      System.out.println("audit log: " + amount);
    }

    private void sendNotification(int amount) {
      System.out.println("send notification: " + amount);
    }
  }

  // Good: 공유 상태를 변경하는 부분만 동기화한다
  //   - 로그와 알림은 락 밖에서 실행한다
  //   - 락 보유 시간이 짧아진다
  //   - 처리량이 좋아진다
  static class Account {

    private int balance = 0;

    public synchronized int getBalance() {
      return balance;
    }

    public void withdraw(int amount) {
      validate(amount);

      synchronized (this) {
        balance -= amount;         // 임계 구역만 보호
      }

      writeAuditLog(amount);
      sendNotification(amount);
    }

    private void validate(int amount) {
      if (amount <= 0) {
        throw new IllegalArgumentException("amount must be positive");
      }
    }

    private void writeAuditLog(int amount) {
      System.out.println("audit log: " + amount);
    }

    private void sendNotification(int amount) {
      System.out.println("send notification: " + amount);
    }
  }
}
