package com.eomcs.tdd.step15.refactor;

class Bank {
  Money reduce(Expression source, String to) {
    // reduce() 메서드에서 타입 캐스팅을 하는 지저분한(ugly) 코드를 제거한다.
    // Expression에게 reduce()를 위임하도록 변경한다.
    return source.reduce(to);
  }
}
