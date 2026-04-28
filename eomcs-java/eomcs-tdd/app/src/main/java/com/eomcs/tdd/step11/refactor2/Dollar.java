package com.eomcs.tdd.step11.refactor2;

// Money에서 times()를 구현했기 때문에 하위 클래스에서 제거한다.
//
class Dollar extends Money {

  Dollar(int amount, String currency) {
    super(amount, currency);
  }
}
