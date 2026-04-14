package com.eomcs.tdd.ch12;

// Chapter 12: Addition, Finally - 최종 결과
//
// Expression 인터페이스: Money와 Sum이 공통으로 구현
// bank.reduce()가 둘 중 어느 것이든 처리할 수 있게 한다.
interface Expression {
  Money reduce(Bank bank, String to);
}
