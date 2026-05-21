package com.eomcs.tdd.step19.green;

interface Expression {
  Money reduce(Bank bank, String to);

  Expression plus(Expression addend);

  // times() 메서드 선언 추가
  Expression times(int multiplier);
}
