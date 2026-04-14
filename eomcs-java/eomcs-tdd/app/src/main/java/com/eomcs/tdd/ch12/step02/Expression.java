package com.eomcs.tdd.ch12.step02;

// [step02 - Green] Expression 인터페이스
//
// plus()의 반환 타입으로 사용된다.
// Sum과 Money 모두 이 인터페이스를 구현함으로써
// bank.reduce()가 둘 중 어느 것이든 처리할 수 있다.
//
// "Expression"이라는 이름은 수식(표현식) 개념에서 왔다.
// $5, $5 + $5, ($5 + 10 CHF) × 2 모두 Expression이다.
interface Expression {
  Money reduce(Bank bank, String to);
}
