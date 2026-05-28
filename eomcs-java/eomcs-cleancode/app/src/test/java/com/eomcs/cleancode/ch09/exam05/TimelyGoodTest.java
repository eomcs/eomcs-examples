package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood5.Calculator;
import org.junit.jupiter.api.Test;

// 예제 5: T — Timely (적시에 작성)
//
// Bad: 구현 후 테스트를 작성해 테스트가 설계를 이끌지 못한다.
// Good: TDD 방식으로 테스트를 먼저 작성한다.
//       테스트 이름이 요구사항을 표현하고, 필요한 코드만 작성하게 유도한다.
//       불필요한 코드가 생기지 않고, 요구사항이 테스트로 살아 있다.
class TimelyGoodTest {

  @Test
  void 두_수를_더한다() {
    Calculator calculator = new Calculator();

    int result = calculator.add(2, 3);

    assertEquals(5, result);
  }
}
