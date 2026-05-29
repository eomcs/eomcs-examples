package com.eomcs.cleancode.ch13.exam02;

// 예제 4: 데드락 - 서로 자원을 기다리면서 영원히 멈추는 상태
public class BadAndGood4 {

  private BadAndGood4() {}

  // Bad: 두 메서드가 락을 서로 반대 순서로 획득한다
  //   - Thread1: lock1 획득 → lock2 대기
  //   - Thread2: lock2 획득 → lock1 대기
  //   - 서로를 기다리며 영원히 멈춤 (데드락)
  static class BadDeadlockExample {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void methodA() {
      synchronized (lock1) {
        synchronized (lock2) {
          System.out.println("A");
        }
      }
    }

    public void methodB() {
      synchronized (lock2) { // lock1과 반대 순서로 획득
        synchronized (lock1) {
          System.out.println("B");
        }
      }
    }
  }

  // Good: 모든 메서드에서 락 획득 순서를 일관되게 유지한다
  //   - 항상 lock1 → lock2 순서로 획득한다
  //   - 어떤 스레드도 lock2를 먼저 가진 상태로 lock1을 기다리지 않는다
  //   - 데드락이 발생하지 않는다
  static class DeadlockExample {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void methodA() {
      synchronized (lock1) {
        synchronized (lock2) {
          System.out.println("A");
        }
      }
    }

    public void methodB() {
      synchronized (lock1) { // 항상 lock1 → lock2 순서로 획득
        synchronized (lock2) {
          System.out.println("B");
        }
      }
    }
  }
}
