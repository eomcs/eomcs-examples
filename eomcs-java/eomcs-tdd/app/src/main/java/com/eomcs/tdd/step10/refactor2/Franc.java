package com.eomcs.tdd.step10.refactor2;

class Franc extends Money {
  // - currency 필드, currency()를 Money로 이동.

  Franc(int amount) {
    // 수퍼 생성자에 currency 값을 전달하도록 변경
    super(amount, "CHF");
  }

  @Override
  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
