package com.eomcs.tdd.ch10.step04_green;

// [step04_green] 실험 취소(Back out)와 방어적 코딩
class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }

  @Override
  Money times(int multiplier) {
    // 실험 취소 (Back out) : times() 메서드의 반환 타입을 Money로 변경한 실험을 취소한다.
    return new Franc(amount * multiplier, currency);
  }
}
