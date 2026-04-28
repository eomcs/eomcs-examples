package com.eomcs.tdd.step10.refactor2;

class Dollar extends Money {
  // - currency 필드, currency()를 Money로 이동.

  Dollar(int amount) {
    // 수퍼 생성자에 currency 값을 전달하도록 변경
    super(amount, "USD");
  }

  @Override
  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
