package com.eomcs.cleancode.ch09.exam05;

import java.time.Clock;
import java.time.LocalDateTime;

// 예제 3: R — Repeatable (반복 가능) - TimeService
public class BadAndGood3 {

  private BadAndGood3() {}

  static class TimeService {
    private final Clock clock;

    TimeService(Clock clock) {
      this.clock = clock;
    }

    int getCurrentHour() {
      return LocalDateTime.now(clock).getHour();
    }
  }
}
