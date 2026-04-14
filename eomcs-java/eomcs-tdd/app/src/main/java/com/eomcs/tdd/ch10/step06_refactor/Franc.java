package com.eomcs.tdd.ch10.step06_refactor;

// [step06_refactor] times()를 Money로 올림 (push up)
class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }
}
