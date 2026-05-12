package com.eomcs.tdd.step15.refactor;

interface Expression {
  // Bank에서 계산을 위임할 때 호출할 reduce() 메서드 선언을 추가한다.
  Money reduce(String to);
}
