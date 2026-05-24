package com.eomcs.cleancode.ch04.exam04;

public class BadAndGood3 {

  // Bad: 오해할 여지가 있는 주석 (Misleading Comments)
  // - "Returns when the connection is closed"는 거짓말이다.
  // - 실제로는 연결이 닫힐 때가 아니라 timeout이 지나면 반환한다.
  // - 오해를 유발하는 주석은 없는 것보다 더 나쁘다.
  static class BadConnection {
    // Returns when the connection is closed
    void waitForClose(long timeoutMillis) {
      try { Thread.sleep(timeoutMillis); } catch (InterruptedException ignored) {}
    }
  }

  // Good: 함수 이름이 실제 동작을 정확히 설명한다.
  // - waitForCloseOrTimeout이라는 이름이 연결 종료 또는 timeout 두 가지 경우를 모두 드러낸다.
  // - 주석 없이도 호출자가 동작을 올바르게 예측할 수 있다.
  static class GoodConnection {
    void waitForCloseOrTimeout(long timeoutMillis) {
      try { Thread.sleep(timeoutMillis); } catch (InterruptedException ignored) {}
    }
  }
}
