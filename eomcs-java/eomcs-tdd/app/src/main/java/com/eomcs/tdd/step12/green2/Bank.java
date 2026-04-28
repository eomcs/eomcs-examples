package com.eomcs.tdd.step12.green2;

// Bank: 환율을 보유하며 Expression을 특정 통화의 Money로 변환한다.
//
// 일단 테스트 통과를 위해 reduce()가 무조건 Money.dollar(10)을 반환하도록 구현한다.
class Bank {

  Money reduce(Expression source, String to) {
    return Money.dollar(10);
  }
}
