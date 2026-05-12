package com.eomcs.tdd.step14.green;

class Bank {

  // Sum 표현식 직접 계산하기
  Money reduce(Expression source, String to) {
    Sum sum = (Sum) source; // Expression을 Sum으로 캐스팅
    int amount = sum.augend.amount + sum.addend.amount; // augend와 addend의 금액을 합산
    return new Money(amount, to); // 합산된 금액으로 새 Money 객체 반환
  }
}
