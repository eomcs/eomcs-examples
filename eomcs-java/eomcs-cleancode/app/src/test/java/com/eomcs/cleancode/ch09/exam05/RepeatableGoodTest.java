package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood3.TimeService;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

// 예제 3: R — Repeatable (반복 가능)
//
// Bad: LocalDateTime.now()에 의존해 실행 시간마다 결과가 달라진다.
// Good: Clock.fixed()로 시간을 고정해 언제 어느 환경에서 실행해도
//       항상 동일한 결과를 보장한다.
class RepeatableGoodTest {

  @Test
  void 현재_시간_테스트() {
    Clock fixedClock = Clock.fixed(
        Instant.parse("2024-01-01T10:00:00Z"),
        ZoneOffset.UTC
    );
    TimeService service = new TimeService(fixedClock);

    assertEquals(10, service.getCurrentHour());
  }
}
