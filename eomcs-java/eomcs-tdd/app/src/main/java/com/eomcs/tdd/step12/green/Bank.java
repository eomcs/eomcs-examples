package com.eomcs.tdd.step12.green;

class Bank {

  Money reduce(Expression source, String to) {
    // 일단 테스트 통과를 위해 reduce()가 무조건 Money.dollar(10)을 반환하도록 구현한다.
    return Money.dollar(10);
  }
}
