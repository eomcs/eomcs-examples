package com.eomcs.tdd.step16.refactor2;

interface Expression {
  Money reduce(Bank bank, String to);
}
