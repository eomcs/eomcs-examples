package com.eomcs.tdd.step19.red;

interface Expression {
  Money reduce(Bank bank, String to);

  Expression plus(Expression addend);
}
