package com.eomcs.tdd.step13.green1;

// Expression 인터페이스를 구현하는 Sum 클래스 정의
class Sum implements Expression {
  Money augend; // 피가수
  Money addend; // 가수

  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }
}
