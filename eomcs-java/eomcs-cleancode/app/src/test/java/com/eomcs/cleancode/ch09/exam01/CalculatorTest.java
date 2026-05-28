package com.eomcs.cleancode.ch09.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam01.BadAndGood1.GoodCalculator;
import org.junit.jupiter.api.Test;

// TDD 단계별 테스트 - Calculator
//
// 1단계: 두_수를_더한다 → Calculator 없으므로 컴파일 실패 (실패하는 테스트)
// 2단계: add(a, b) { return 5; } 작성 → 첫 번째 테스트 통과 (최소 구현)
// 3단계: 다른_두_수도_더한다 추가 → add(2,3)=5 고정값이라 실패
// 4단계: add(a, b) { return a + b; } 일반화 → 두 테스트 모두 통과
class CalculatorTest {

  // 1단계: 실패하는 첫 번째 테스트
  // → 이 시점에는 Calculator가 없으므로 컴파일 실패(= 실패 테스트)
  @Test
  void 두_수를_더한다() {
    GoodCalculator calculator = new GoodCalculator();

    int result = calculator.add(2, 3);

    assertEquals(5, result);
  }

  // 3단계: 다음 실패 테스트
  // → add() 가 return 5 고정값이면 이 테스트는 실패한다
  @Test
  void 다른_두_수도_더한다() {
    GoodCalculator calculator = new GoodCalculator();

    int result = calculator.add(10, 20);

    assertEquals(30, result);
  }
}
