package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood5.Calculator;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - T (Timely)
//
// 문제점:
// - 이미 구현된 코드를 검증하기 위해 나중에 테스트를 작성했다.
// - 테스트가 설계를 이끌지 못하고, 기존 구현을 뒤따를 뿐이다.
// - 테스트 이름이 요구사항보다 구현을 설명한다.
// - 이미 완성된 코드에 맞춰 테스트를 끼워 맞추게 된다.
class TimelyBadTest {

  @Test
  void addTest() {
    Calculator calculator = new Calculator();

    assertEquals(5, calculator.add(2, 3));
  }
}
