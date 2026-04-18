package com.eomcs.advanced.concurrency.exam01;

// Thread 생성 방법:
// - java.lang 패키지에 소속되어 있다.
// - 스레드(Thread)는 프로세스 내에서 독립적으로 실행되는 실행 흐름이다.
// - Java에서 스레드를 생성하는 방법은 세 가지이다.
//
// 1. Thread 클래스 상속
//    - Thread 클래스를 상속하고 run() 메서드를 재정의한다.
//    - 단점: 이미 다른 클래스를 상속 중이면 사용할 수 없다 (단일 상속 제한).
//
// 2. Runnable 인터페이스 구현
//    - Runnable 인터페이스의 run() 메서드를 구현한 객체를 Thread 생성자에 전달한다.
//    - 상속 제한 없이 사용할 수 있다.
//
// 3. 람다로 Runnable 구현 (Java 8+)
//    - Runnable이 함수형 인터페이스이므로 람다로 간결하게 표현할 수 있다.
//
// 주요 메서드:
//   start()           : 새 스레드를 생성하고 run()을 실행한다. run()을 직접 호출하면 안 된다.
//   run()             : 스레드가 실행할 코드. start()에 의해 새 스레드에서 호출된다.
//   Thread.sleep(ms)  : 현재 스레드를 지정한 시간(밀리초) 동안 일시 정지한다.
//   getName()         : 스레드 이름을 반환한다.
//   Thread.currentThread() : 현재 실행 중인 스레드 객체를 반환한다.
//

public class App {

  // 1. Thread 클래스 상속
  static class MyThread extends Thread {

    public MyThread(String name) {
      super(name); // 스레드 이름 설정
    }

    @Override
    public void run() {
      for (int i = 1; i <= 3; i++) {
        System.out.println(getName() + " 실행 중: " + i);
        try {
          Thread.sleep(100); // 0.1초 대기
        } catch (InterruptedException e) {
          System.out.println(getName() + " 인터럽트 발생");
          return;
        }
      }
      System.out.println(getName() + " 종료");
    }
  }

  // 2. Runnable 인터페이스 구현
  static class MyRunnable implements Runnable {

    private final String name;

    public MyRunnable(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      for (int i = 1; i <= 3; i++) {
        System.out.println(name + " 실행 중: " + i);
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          System.out.println(name + " 인터럽트 발생");
          return;
        }
      }
      System.out.println(name + " 종료");
    }
  }

  public static void main(String[] args) throws InterruptedException {

    // 1. Thread 클래스 상속으로 스레드 생성
    System.out.println("[1. Thread 클래스 상속]");
    MyThread t1 = new MyThread("Thread-A");
    t1.start(); // 새 스레드 시작. run()을 직접 호출하면 안 된다.
    t1.join();  // t1이 종료될 때까지 현재 스레드(main) 대기

    // 2. Runnable 인터페이스 구현으로 스레드 생성
    System.out.println("\n[2. Runnable 인터페이스 구현]");
    Thread t2 = new Thread(new MyRunnable("Thread-B"));
    t2.start();
    t2.join();

    // 3. 람다로 Runnable 구현 (Java 8+)
    System.out.println("\n[3. 람다로 Runnable 구현]");
    Thread t3 = new Thread(() -> {
      for (int i = 1; i <= 3; i++) {
        System.out.println(Thread.currentThread().getName() + " 실행 중: " + i);
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          return;
        }
      }
      System.out.println(Thread.currentThread().getName() + " 종료");
    }, "Thread-C");
    t3.start();
    t3.join();

    // 4. 여러 스레드 동시 실행
    System.out.println("\n[4. 여러 스레드 동시 실행]");
    Thread[] threads = {
        new MyThread("T1"),
        new MyThread("T2"),
        new MyThread("T3")
    };

    for (Thread t : threads) {
      t.start(); // 세 스레드가 동시에 실행됨 - 출력 순서가 섞일 수 있다
    }
    for (Thread t : threads) {
      t.join();  // 모든 스레드가 종료될 때까지 대기
    }

    // 5. 스레드 기본 정보 확인
    System.out.println("\n[5. 스레드 정보]");
    Thread main = Thread.currentThread();
    System.out.println("현재 스레드 이름: " + main.getName());
    System.out.println("현재 스레드 ID:   " + main.threadId());
    System.out.println("현재 스레드 우선순위: " + main.getPriority());

    // 이름 지정 없이 생성하면 자동으로 "Thread-N" 형식의 이름이 붙는다
    Thread unnamed = new Thread(() -> {});
    System.out.println("자동 이름: " + unnamed.getName());

    // 이름을 직접 지정할 수 있다
    Thread named = new Thread(() -> {}, "my-worker");
    System.out.println("지정 이름: " + named.getName());

    System.out.println("\nmain 스레드 종료");
  }
}
