package com.eomcs.advanced.concurrency.exam01;

// Thread 상태 (Thread.State):
//
//   NEW           : 스레드 객체가 생성되었지만 start()가 호출되지 않은 상태
//   RUNNABLE      : 실행 중이거나 실행 가능한 상태 (CPU 할당 대기 포함)
//   BLOCKED       : synchronized 블록/메서드의 락을 획득하려고 대기 중인 상태
//   WAITING       : 다른 스레드의 신호를 무한정 대기하는 상태
//                   (Object.wait(), Thread.join(), LockSupport.park() 호출 시)
//   TIMED_WAITING : 일정 시간 동안 대기하는 상태
//                   (Thread.sleep(ms), Object.wait(ms), Thread.join(ms) 호출 시)
//   TERMINATED    : run() 메서드가 완료되어 스레드가 종료된 상태
//
// 상태 전환 흐름:
//
//   NEW ──start()──> RUNNABLE ──run() 완료──> TERMINATED
//                      │  ↑
//                      │  │ 락 획득
//                      ↓  │
//                   BLOCKED (synchronized 락 대기)
//                      │  ↑
//                      │  │ notify()/notifyAll()/시간 만료/join 완료
//                      ↓  │
//              WAITING / TIMED_WAITING
//

public class App3 {

  static final Object LOCK = new Object();

  public static void main(String[] args) throws InterruptedException {

    // 1. NEW 상태
    System.out.println("[1. NEW 상태]");
    Thread t = new Thread(() -> {}, "StateCheck");
    System.out.println("start() 전: " + t.getState()); // NEW

    // 2. TERMINATED 상태
    System.out.println("\n[2. TERMINATED 상태]");
    t.start();
    t.join();
    System.out.println("종료 후: " + t.getState()); // TERMINATED

    // 3. TIMED_WAITING 상태 (sleep)
    System.out.println("\n[3. TIMED_WAITING 상태 - sleep 중]");
    Thread sleeper =
        new Thread(
            () -> {
              try {
                Thread.sleep(500);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Sleeper");

    sleeper.start();
    Thread.sleep(50); // main 스레드 잠깐 대기: sleeper가 sleep에 진입할 시간
    System.out.println("sleep 중: " + sleeper.getState()); // TIMED_WAITING
    sleeper.join();
    System.out.println("종료 후: " + sleeper.getState()); // TERMINATED

    // 4. WAITING 상태 (join)
    System.out.println("\n[4. WAITING 상태 - join 중]");
    Thread longRunner =
        new Thread(
            () -> {
              try {
                Thread.sleep(500);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "LongRunner");

    Thread waiter =
        new Thread(
            () -> {
              try {
                longRunner.join(); // longRunner 종료 대기 → WAITING
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Waiter");

    longRunner.start();
    waiter.start();
    Thread.sleep(50);
    System.out.println("join 중인 waiter: " + waiter.getState()); // WAITING
    longRunner.join();
    waiter.join();

    // 5. BLOCKED 상태 (synchronized 락 대기)
    System.out.println("\n[5. BLOCKED 상태 - synchronized 락 대기]");
    Thread lockHolder =
        new Thread(
            () -> {
              synchronized (LOCK) {
                try {
                  Thread.sleep(500); // 락을 잡은 채 대기
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                }
              }
            },
            "LockHolder");

    Thread lockWaiter =
        new Thread(
            () -> {
              synchronized (LOCK) { // LockHolder가 락을 잡고 있으므로 BLOCKED
                System.out.println("  lockWaiter: 락 획득");
              }
            },
            "LockWaiter");

    lockHolder.start();
    Thread.sleep(50); // lockHolder가 락을 잡을 시간
    lockWaiter.start();
    Thread.sleep(50); // lockWaiter가 락 대기 상태에 진입할 시간
    System.out.println("락 대기 중인 lockWaiter: " + lockWaiter.getState()); // BLOCKED
    lockHolder.join();
    lockWaiter.join();

    // 6. isAlive() - 스레드 생존 여부 확인
    System.out.println("\n[6. isAlive()]");
    Thread alive =
        new Thread(
            () -> {
              try {
                Thread.sleep(300);
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              }
            },
            "Alive");

    System.out.println("start() 전: " + alive.isAlive()); // false
    alive.start();
    Thread.sleep(50);
    System.out.println("실행 중:    " + alive.isAlive()); // true
    alive.join();
    System.out.println("종료 후:    " + alive.isAlive()); // false

    System.out.println("\nmain 스레드 종료");
  }
}
