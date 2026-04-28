package com.eomcs.tdd.step10.green;

class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  // Money의 currency() 메서드 구현
  @Override
  String currency() {
    return "USD"; // 하드코딩된 통화 문자열을 반환한다.
  }

  @Override
  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
