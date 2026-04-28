package com.eomcs.tdd.step13.green3;

// reduce() 메서드에서 Money 파라미터를 받았을 때 처리하는 로직을 추가한다.
class Bank {
  Money reduce(Expression source, String to) {
    if (source instanceof Money) {
      return ((Money) source).reduce(to); // Money의 reduce() 메서드를 호출하여 축약된 Money 객체 반환
    }
    Sum sum = (Sum) source; // Expression을 Sum으로 캐스팅
    return sum.reduce(to); // 계산을 Sum에게 위임하여 결과를 반환
  }
}
