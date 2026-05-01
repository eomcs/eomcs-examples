package com.eomcs.tdd.step10.refactor6;

class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }

  // times() 메서드를 Money로 pull up 한다.
}
