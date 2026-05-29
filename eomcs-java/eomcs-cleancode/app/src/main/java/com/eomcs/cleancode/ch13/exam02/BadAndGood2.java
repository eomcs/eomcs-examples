package com.eomcs.cleancode.ch13.exam02;

// 예제 2: 실행 순서 문제 - 동시성에서는 실행 순서를 예측할 수 없다
public class BadAndGood2 {

  private BadAndGood2() {}

  // Bad: 공유 변수에 대한 가시성(visibility)이 보장되지 않는다
  //   - thread2가 먼저 실행되면 0 출력
  //   - thread1 이후 실행되면 1 출력
  //   - 결과가 일정하지 않다
  //   - JVM은 스레드마다 변수를 캐시할 수 있어 thread1의 쓰기가 thread2에게 보이지 않을 수 있다
  static class BadExample {

    private int value = 0;

    public void thread1() {
      value = 1;
    }

    public void thread2() {
      System.out.println(value);
    }
  }

  // Good: volatile로 변수의 가시성을 보장한다
  //   - volatile은 모든 스레드가 항상 메인 메모리에서 값을 읽고 쓰도록 강제한다
  //   - thread1의 쓰기가 thread2에게 반드시 보인다
  //   - 단, 실행 순서 자체를 고정하지는 않는다 — 순서 제어가 필요하면 명시적 동기화가 필요하다
  static class Example {

    private volatile int value = 0;

    public void thread1() {
      value = 1;
    }

    public void thread2() {
      System.out.println(value);
    }
  }
}
