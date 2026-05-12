package com.eomcs.tdd.step17.refactor2;

interface Expression {
  Money reduce(Bank bank, String to);

  Expression plus(Expression addend);
}
