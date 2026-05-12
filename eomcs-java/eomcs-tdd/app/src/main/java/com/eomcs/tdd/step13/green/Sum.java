package com.eomcs.tdd.step13.green;

// Sum 클래스가 Expression 인터페이스를 구현
class Sum implements Expression {

  Money augend; // 피가수
  Money addend; // 가수

  // 생성자에서 필드 초기화
  Sum(Money augend, Money addend) {
    this.augend = augend;
    this.addend = addend;
  }
}
