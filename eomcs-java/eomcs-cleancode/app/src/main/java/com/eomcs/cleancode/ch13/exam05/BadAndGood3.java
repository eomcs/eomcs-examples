package com.eomcs.cleancode.ch13.exam05;

// 예제 3: Dining Philosophers - 여러 자원을 동시에 필요로 할 때 생기는 교착 문제
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 각 스레드가 자원을 다른 순서로 획득한다
  //   - 각 스레드가 leftChopstick을 잡고 rightChopstick을 기다린다
  //   - 원탁의 경우 순환 대기가 생겨 deadlock 발생 가능
  //   - 자원 획득 순서가 일관되지 않다
  static class BadPhilosopher implements Runnable {

    private final Object leftChopstick;
    private final Object rightChopstick;

    BadPhilosopher(Object leftChopstick, Object rightChopstick) {
      this.leftChopstick = leftChopstick;
      this.rightChopstick = rightChopstick;
    }

    @Override
    public void run() {
      synchronized (leftChopstick) {
        synchronized (rightChopstick) {
          eat();
        }
      }
    }

    private void eat() {
      System.out.println("eat");
    }
  }

  // Bad 사용 예: c1→c2, c2→c3, c3→c1 순환 구조로 deadlock 가능
  static class BadDiningTable {

    public void run() {
      Object c1 = new Object();
      Object c2 = new Object();
      Object c3 = new Object();

      new Thread(new BadPhilosopher(c1, c2)).start();
      new Thread(new BadPhilosopher(c2, c3)).start();
      new Thread(new BadPhilosopher(c3, c1)).start(); // c3 잡고 c1 대기 → 순환
    }
  }

  // Good: id를 가진 젓가락으로 획득 순서를 통일한다
  //   - 항상 id가 작은 젓가락부터 획득한다
  //   - 순환 대기가 깨진다
  //   - deadlock 가능성이 줄어든다
  static class Chopstick {

    private final int id;

    Chopstick(int id) {
      this.id = id;
    }

    int id() {
      return id;
    }
  }

  static class SafePhilosopher implements Runnable {

    private final Chopstick first;
    private final Chopstick second;

    SafePhilosopher(Chopstick left, Chopstick right) {
      if (left.id() < right.id()) {
        this.first = left;
        this.second = right;
      } else {
        this.first = right;
        this.second = left;
      }
    }

    @Override
    public void run() {
      synchronized (first) {
        synchronized (second) {
          eat();
        }
      }
    }

    private void eat() {
      System.out.println("eat");
    }
  }

  // Good 사용 예: 모든 철학자가 항상 id가 작은 젓가락부터 잡는다
  static class SafeDiningTable {

    public void run() {
      Chopstick c1 = new Chopstick(1);
      Chopstick c2 = new Chopstick(2);
      Chopstick c3 = new Chopstick(3);

      new Thread(new SafePhilosopher(c1, c2)).start(); // 1→2
      new Thread(new SafePhilosopher(c2, c3)).start(); // 2→3
      new Thread(new SafePhilosopher(c3, c1)).start(); // 내부에서 1→3으로 재정렬
    }
  }
}
