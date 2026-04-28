package com.eomcs.tdd.step13.green2;

// 진짜 합계를 계산하는 reduce() 메서드 추가
class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }

  // Sum을 reduce하는 메서드 추가
  Money reduce(String to) {
    int amount = augend.amount + addend.amount; // augend와 addend의 금액을 합산
    return new Money(amount, to); // 합산된 금액으로 새 Money 객체 반환
  }
}
