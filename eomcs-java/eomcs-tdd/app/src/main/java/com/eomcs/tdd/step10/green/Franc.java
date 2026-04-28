package com.eomcs.tdd.step10.green;

class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  // Money의 currency() 메서드 구현
  @Override
  String currency() {
    return "CHF"; // 하드코딩된 통화 문자열을 반환한다.
  }

  @Override
  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
