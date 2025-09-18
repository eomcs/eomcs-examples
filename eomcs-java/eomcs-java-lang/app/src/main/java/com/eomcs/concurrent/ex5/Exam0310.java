// synchronized - 메서드를 동기화시킴
package com.eomcs.concurrent.ex5;

public class Exam0310 {
  static class Counter {
    long value;

    public Counter(long value) {
      this.value = value;
    }
  }

  // 비동기 실행
  // - 여러 스레드가 동시에 진입 가능!
  static void print1(String threadName, Counter counter) {
    System.out.printf("[%s] 출력 시작 ----------\n", threadName);

    for (int i= 0; i < counter.value; i++) {
      System.out.printf("%s ==> %d\n", threadName, i);
    }

    System.out.printf("---------- [%s] 출력 끝\n", threadName);
  }

  // 동기 실행
  // - 한 번에 한 스레드만 진입 가능!
  synchronized static void print2(String threadName, Counter counter) {
    System.out.printf("[%s] 출력 시작 ----------\n", threadName);

    for (int i  = 0; i < counter.value; i++) {
      System.out.printf("%s ==> %d\n", threadName, i);
    }

    System.out.printf("---------- [%s] 출력 끝\n", threadName);
  }

  // 동기화 블록
  // - 한 번에 한 스레드만 진입 가능!
  static void print3(String threadName, Counter counter) {
    System.out.printf("[%s] 출력 시작 ----------\n", threadName);

    synchronized (counter) {
      System.out.printf("[%s] $$$$$$$$$$$$$$$$$$$$$$\n", threadName);
      for (int i  = 0; i < 1000; i++) {
        System.out.printf("%s ==> %d\n", threadName, i);
      }
    }

    System.out.printf("---------- [%s] 출력 끝\n", threadName);
  }

  static class Worker extends Thread {
    Counter counter;

    public Worker(String name, Counter counter) {
      super(name);
      this.counter = counter;
    }

    @Override
    public void run() {
      print3(this.getName(), counter);
    }
  }

  public static void main(String[] args) throws Exception {
    Counter counter = new Counter(1000);

    Worker w1 = new Worker("**홍길동", counter);
    Worker w2 = new Worker("임꺽정---->", counter);
    Worker w3 = new Worker("유%관%순", counter);

    w1.start();
    w2.start();
    w3.start();
  }
}
