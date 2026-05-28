package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - R (Repeatable)
//
// 문제점:
// - LocalDateTime.now()를 사용해 실행 시간에 따라 결과가 달라진다.
// - assertEquals(10, now.getHour())는 10시에만 통과하고 나머지 시간에는 실패한다.
// - 환경(시간대, 서버 시각)에 따라 결과가 다르다.
// - CI 서버와 로컬에서 결과가 다를 수 있다.
class RepeatableBadTest {

  @Disabled("실행 시간에 따라 결과가 달라지는 나쁜 테스트 패턴 예시 - 10시에만 통과")
  @Test
  void 현재_시간_테스트() {
    LocalDateTime now = LocalDateTime.now();

    assertEquals(10, now.getHour()); // 실행 시간에 따라 실패
  }
}
