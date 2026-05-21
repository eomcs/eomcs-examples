package com.eomcs.tdd.step18.green;

interface Expression {
  Money reduce(Bank bank, String to);

  Expression plus(Expression addend);
}
