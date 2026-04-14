package com.eomcs.tdd.ch10.step04_red;

// [step04_red] 실험 취소(Back out)와 방어적 코딩
class Dollar extends Money {

  Dollar(int amount, String currency) {
    super(amount, currency);
  }

  @Override
  Money times(int multiplier) {
    // 실험 취소 (Back out) : times() 메서드의 반환 타입을 Money로 변경한 실험을 취소한다.
    return new Dollar(amount * multiplier, currency);
  }
}
