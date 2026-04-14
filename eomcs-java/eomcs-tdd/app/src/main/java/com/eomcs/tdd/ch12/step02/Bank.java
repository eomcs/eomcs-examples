package com.eomcs.tdd.ch12.step02;

// [step02 - Green] Bank
//
// 환율을 보유하며, Expression을 특정 통화의 Money로 변환한다.
// 이번 장에서는 환율 없이 같은 통화끼리 더하는 경우만 다루므로
// 단순히 expression.reduce()를 위임한다.
class Bank {

  Money reduce(Expression source, String to) {
    return source.reduce(this, to);
  }
}
