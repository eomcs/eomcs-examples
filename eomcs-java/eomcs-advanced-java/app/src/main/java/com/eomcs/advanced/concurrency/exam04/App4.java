package com.eomcs.advanced.concurrency.exam04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Condition - 조건 변수:
//
// Condition은 Object.wait()/notify()의 대체로, Lock과 함께 사용하는 조건 대기 메커니즘이다.
//
// Object.wait/notify의 한계:
//   - synchronized 블록 안에서만 사용 가능 (Lock과 함께 사용 불가)
//   - 한 객체에 대기 큐가 하나뿐이다 → 생산자와 소비자를 한꺼번에 깨워야 한다
//
// Condition의 장점:
//   - 하나의 Lock에 Condition을 여러 개 만들 수 있다
//   - 생산자 대기 큐(notFull)와 소비자 대기 큐(notEmpty)를 분리할 수 있다
//   - signal()로 필요한 쪽만 정확하게 깨울 수 있다
//
// 주요 메서드:
//   - await()      : 락을 해제하고 대기. 깨어나면 락 재획득 후 진행
//   - signal()     : 대기 중인 스레드 하나를 깨운다
//   - signalAll()  : 대기 중인 모든 스레드를 깨운다
//
// await() 사용 규칙:
//   - 반드시 while 루프 안에서 호출한다 (if 대신 while)
//   - 깨어났을 때 조건이 여전히 충족되지 않을 수 있기 때문이다 (spurious wakeup)
//     while (!조건) { condition.await(); }
//
// Object.wait/notify ↔ Condition 대응 관계:
//   synchronized(obj) { obj.wait(); }   ↔   lock.lock(); condition.await();
//   synchronized(obj) { obj.notify(); } ↔   lock.lock(); condition.signal();
//

public class App4 {

  // Condition으로 구현한 크기 제한 버퍼 (생산자-소비자 패턴)
  static class BoundedBuffer {

    private final int[] buffer;
    private int count = 0; // 현재 버퍼에 있는 항목 수
    private int putIndex = 0; // 다음에 넣을 위치
    private int takeIndex = 0; // 다음에 꺼낼 위치

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition(); // 버퍼 가득 참 → 생산자 대기
    private final Condition notEmpty = lock.newCondition(); // 버퍼 비어 있음 → 소비자 대기

    public BoundedBuffer(int capacity) {
      buffer = new int[capacity];
    }

    // 생산자: 버퍼에 항목 추가
    public void put(int item) throws InterruptedException {
      lock.lock();
      try {
        while (count == buffer.length) { // 버퍼가 가득 찼으면
          System.out.printf(
              "    [%s] 버퍼 가득 참 (%d/%d) → 대기%n",
              Thread.currentThread().getName(), count, buffer.length);
          notFull.await(); // 락 해제 + 대기 → 소비자가 notFull.signal() 할 때까지
        }
        buffer[putIndex] = item;
        putIndex = (putIndex + 1) % buffer.length;
        count++;
        System.out.printf(
            "    [%s] 생산: %2d (버퍼: %d/%d)%n",
            Thread.currentThread().getName(), item, count, buffer.length);
        notEmpty.signal(); // 소비자 대기 큐에서 하나 깨우기
      } finally {
        lock.unlock();
      }
    }

    // 소비자: 버퍼에서 항목 꺼내기
    public int take() throws InterruptedException {
      lock.lock();
      try {
        while (count == 0) { // 버퍼가 비어 있으면
          System.out.printf(
              "    [%s] 버퍼 비어 있음 → 대기%n", Thread.currentThread().getName());
          notEmpty.await(); // 락 해제 + 대기 → 생산자가 notEmpty.signal() 할 때까지
        }
        int item = buffer[takeIndex];
        takeIndex = (takeIndex + 1) % buffer.length;
        count--;
        System.out.printf(
            "    [%s] 소비: %2d (버퍼: %d/%d)%n",
            Thread.currentThread().getName(), item, count, buffer.length);
        notFull.signal(); // 생산자 대기 큐에서 하나 깨우기
        return item;
      } finally {
        lock.unlock();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // ─────────────────────────────────────────────────────────────
    // 데모 1. BoundedBuffer 생산자-소비자
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 1] Condition - 생산자-소비자 (BoundedBuffer)");
    System.out.println("버퍼 크기: 3  |  생산자 2개(각 5개)  |  소비자 2개(각 5개)");
    System.out.println();

    BoundedBuffer buffer = new BoundedBuffer(3);

    Thread producer1 =
        new Thread(
            () -> {
              try {
                for (int i = 1; i <= 5; i++) {
                  buffer.put(i);
                  Thread.sleep(80);
                }
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "생산자-1");

    Thread producer2 =
        new Thread(
            () -> {
              try {
                for (int i = 11; i <= 15; i++) {
                  buffer.put(i);
                  Thread.sleep(120);
                }
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "생산자-2");

    Thread consumer1 =
        new Thread(
            () -> {
              try {
                for (int i = 0; i < 5; i++) {
                  buffer.take();
                  Thread.sleep(200);
                }
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "소비자-1");

    Thread consumer2 =
        new Thread(
            () -> {
              try {
                for (int i = 0; i < 5; i++) {
                  buffer.take();
                  Thread.sleep(250);
                }
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "소비자-2");

    producer1.start();
    producer2.start();
    consumer1.start();
    consumer2.start();

    producer1.join();
    producer2.join();
    consumer1.join();
    consumer2.join();

    System.out.println();

    // ─────────────────────────────────────────────────────────────
    // 데모 2. Condition 분리의 장점 - 생산자만, 소비자만 선택적으로 깨우기
    // ─────────────────────────────────────────────────────────────
    System.out.println("[데모 2] Condition 분리 - 생산자/소비자를 각각의 Condition으로 관리");
    System.out.println("notFull.signal()  → 대기 중인 생산자 스레드만 깨운다");
    System.out.println("notEmpty.signal() → 대기 중인 소비자 스레드만 깨운다");
    System.out.println();
    System.out.println("Object.notify()를 사용했다면:");
    System.out.println("  생산자·소비자가 같은 대기 큐에 있어 notify()가 생산자를 깨울 수도 있다.");
    System.out.println("  → 소비자가 깨어나야 할 때 생산자가 깨어나 조건을 확인하고 다시 대기");
    System.out.println("  → CPU 낭비, 처리량 저하");
    System.out.println();
    System.out.println("Condition 분리 시:");
    System.out.println("  notFull.signal()  → 생산자 대기 큐에서만 깨움 (소비자 방해 없음)");
    System.out.println("  notEmpty.signal() → 소비자 대기 큐에서만 깨움 (생산자 방해 없음)");
    System.out.println("  → 정확한 스레드만 깨워 불필요한 경쟁 없음");
  }
}
