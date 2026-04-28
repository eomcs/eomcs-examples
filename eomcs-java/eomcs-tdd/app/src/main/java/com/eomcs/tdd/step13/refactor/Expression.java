package com.eomcs.tdd.step13.refactor;

// reduce() 메서드 선언을 추가한다.
interface Expression {
  Money reduce(String to);
}
