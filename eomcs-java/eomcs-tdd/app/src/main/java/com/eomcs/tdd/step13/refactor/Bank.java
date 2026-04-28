package com.eomcs.tdd.step13.refactor;

// reduce() 메서드에서 타입 검사 및 타입 캐스팅을 하는 지저분한(ugly) 코드를 제거한다.
class Bank {
  Money reduce(Expression source, String to) {
    return source.reduce(to);
  }
}
