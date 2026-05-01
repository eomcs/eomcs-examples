package com.eomcs.tdd.step10.refactor4;

class Dollar extends Money {

  Dollar(int amount, String currency) {
    super(amount, currency); // 상위 클래스의 생성자 호출하여 중복 제거
  }

  @Override
  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier, currency);
  }
}
