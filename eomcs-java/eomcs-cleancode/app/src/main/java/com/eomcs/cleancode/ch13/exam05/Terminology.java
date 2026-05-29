package com.eomcs.cleancode.ch13.exam05;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// 기본 용어: 동시성 프로그래밍의 핵심 개념
public class Terminology {

  private Terminology() {}

  // Bound Resource (한정된 자원): 개수나 크기가 제한된 자원
  //   - 큐 크기가 10으로 제한됨
  //   - 생산자가 너무 많이 넣으면 기다려야 함
  //   - 소비자가 꺼내야 다시 넣을 수 있음
  static class BoundResourceExample {

    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    public void produce(String item) throws InterruptedException {
      queue.put(item); // 큐가 가득 차면 여기서 대기
    }

    public String consume() throws InterruptedException {
      return queue.take(); // 큐가 비면 여기서 대기
    }
  }

  // Mutual Exclusion (상호 배제): 한 번에 하나의 스레드만 공유 자원에 접근하게 하는 것
  //   - 여러 스레드가 동시에 balance를 수정하지 못하게 막음
  //   - 데이터 깨짐을 방지
  static class MutualExclusionExample {

    private int balance = 0;

    public synchronized void deposit(int amount) {
      balance += amount;
    }

    public synchronized void withdraw(int amount) {
      balance -= amount;
    }

    public synchronized int getBalance() {
      return balance;
    }
  }

  // Starvation (기아): 특정 스레드가 너무 오래, 또는 영원히 실행 기회를 얻지 못하는 상태
  //   - 읽기 작업이 계속 들어오면 쓰기 작업이 계속 밀릴 수 있음
  //   - 시스템은 돌아가지만 특정 작업은 진행되지 않음
  //   - ReadWriteLock 의 읽기 우선 정책에서 쓰기 스레드가 기아 상태에 빠질 수 있다
  static class StarvationExample {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private String data = "initial";

    // 읽기 요청이 끊이지 않으면 쓰기 스레드는 readLock이 모두 해제될 때까지 대기만 한다
    public String read() {
      lock.readLock().lock();
      try {
        return data;
      } finally {
        lock.readLock().unlock();
      }
    }

    public void write(String newData) {
      lock.writeLock().lock(); // 읽기 스레드가 많으면 여기서 오래 대기
      try {
        data = newData;
      } finally {
        lock.writeLock().unlock();
      }
    }
  }

  // Deadlock (교착 상태): 두 개 이상의 스레드가 서로가 가진 자원을 기다리며 멈추는 상태
  //   - Thread A: lock1 잡고 lock2 기다림
  //   - Thread B: lock2 잡고 lock1 기다림
  //   - 둘 다 기다리기만 함, 프로그램이 멈춤
  static class DeadlockExample {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void methodA() {
      synchronized (lock1) {
        synchronized (lock2) { // lock1 → lock2 순서
          System.out.println("A");
        }
      }
    }

    public void methodB() {
      synchronized (lock2) {
        synchronized (lock1) { // lock2 → lock1 순서 (반대) → 데드락 위험
          System.out.println("B");
        }
      }
    }
  }

  // Livelock (활성 교착 상태): 스레드들이 계속 움직이지만 실제 진전은 없는 상태
  //   - CPU는 사용하지만 작업은 완료되지 않음
  //   - "A: 네가 먼저 가", "B: 아니, 네가 먼저 가"를 계속 반복하는 것과 같다
  static class LivelockExample {

    private volatile boolean resourceInUse = false;

    // 두 스레드가 동시에 실행하면 서로 양보만 반복하며 진전이 없다
    public void process(String name) {
      while (resourceInUse) {
        System.out.println(name + ": 양보");
        resourceInUse = false; // 양보했다가
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          return;
        }
        resourceInUse = true; // 다시 사용하려 하다가 반복
      }
      System.out.println(name + ": 실행");
    }
  }
}
