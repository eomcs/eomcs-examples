package com.eomcs.tdd.step01.refactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

// Refactor 단계
// - 하드코딩된 부분을 일반화하여 중복을 제거한다.
// - Refactor 단계에서는 테스트와 코드 사이의 논리적 중복을 제거하여 의존성을 없앤다.

// 중복 예:
// - DollarTest의 5 * 2 = 10 테스트와 Dollar의 amount = 10 코드는 논리적 중복으로 판단한다.

// 중복의 문제점:
// - 코드가 중복되면 코드 사이에 "의존" 관계가 형성된다.
// - 코드 사이에 의존 관계가 형성되면 한 쪽 코드를 바꿀 때 다른 쪽 코드도 바꿔야 하는 문제가 발생한다.
//     - 예) DollarTest의 테스트 값을 바꾸면 Dollar 코드의 값도 바꿔야 한다.
// - 중복 관계가 아니라면 한 쪽 코드를 바꾼다고 해서 다른 쪽 코드를 바꿔야 할 이유가 없다.
// - 즉 테스트(DollarTest)와 코드(Dollar) 사이에 "의존" 관계가 형성되었고 이런 의존성을 나타내는 증상이 "중복"이다.

// 중복 제거와 그 이점:
// - 코드 내의 논리적 중복을 제거하는 것이 코드 간의 의존성을 없애는 방법이다.
// - 의존성이 없으면 테스트와 코드가 서로 독립적으로 존재할 수 있다.

class DollarTest {
  @Test
  void testMultiplication() {
    // $5 × 2 = $10 이어야 한다
    Dollar five = new Dollar(5);
    five.times(2);
    assertEquals(10, five.amount);
  }
}
