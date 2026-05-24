package com.eomcs.cleancode.ch04.exam04;

import java.time.Duration;

public class BadAndGood14 {

  // Bad: 전역 정보 (Nonlocal Information)
  // - 현재 connect() 메서드와 무관한 SystemConfig의 설정값을 주석으로 설명한다.
  // - SystemConfig의 타임아웃 값이 바뀌면 주석은 거짓말이 된다.
  // - 주석이 설명하는 정보가 코드와 멀리 떨어져 있을수록 동기화가 깨질 위험이 크다.
  static class BadConnectionService {
    // Default timeout is configured in SystemConfig as 30 seconds.
    void connect() {
      System.out.println("연결");
    }
  }

  // Good: 전역 설정에 의존하는 대신 파라미터로 직접 받는다.
  // - 타임아웃 값을 connect()가 직접 파라미터로 받으므로 주석이 필요 없다.
  // - 호출자가 타임아웃을 명시적으로 전달하므로 코드 자체가 설명이 된다.
  static class GoodConnectionService {
    void connect(Duration timeout) {
      System.out.println("연결 (타임아웃: " + timeout.getSeconds() + "초)");
    }
  }
}
