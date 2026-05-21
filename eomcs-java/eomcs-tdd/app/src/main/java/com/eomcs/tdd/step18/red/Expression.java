package com.eomcs.tdd.step18.red;

interface Expression {
  Money reduce(Bank bank, String to);

  Expression plus(Expression addend);
}
