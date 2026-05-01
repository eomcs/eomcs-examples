package com.eomcs.tdd.step10.refactor6;

class Dollar extends Money {

  Dollar(int amount, String currency) {
    super(amount, currency);
  }

  // times() 메서드를 Money로 pull up 한다.
}
