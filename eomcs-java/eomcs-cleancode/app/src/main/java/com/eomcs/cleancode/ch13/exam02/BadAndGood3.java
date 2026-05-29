package com.eomcs.cleancode.ch13.exam02;

// 예제 3: 경쟁 조건 - 결과가 실행 타이밍에 따라 달라지는 문제
public class BadAndGood3 {

  private BadAndGood3() {}

  // Bad: 조건 검사와 변경이 원자적으로 처리되지 않는다
  //   - 두 스레드가 동시에 stock > 0 검사 → 둘 다 true
  //   - 둘 다 stock-- 실행 → 재고 -1 가능
  //   - 조건 검사(check)와 상태 변경(act) 사이에 다른 스레드가 개입할 수 있다
  static class BadInventory {

    private int stock = 10;

    public void sell() {
      if (stock > 0) {
        stock--;
      }
    }

    public int getStock() {
      return stock;
    }
  }

  // Good: 조건 검사와 변경을 하나의 synchronized 블록으로 묶어 원자성을 보장한다
  //   - 한 번에 하나의 스레드만 sell()을 실행한다
  //   - 검사와 변경이 분리될 수 없다
  static class Inventory {

    private int stock = 10;

    public synchronized void sell() {
      if (stock > 0) {
        stock--;
      }
    }

    public synchronized int getStock() {
      return stock;
    }
  }
}
