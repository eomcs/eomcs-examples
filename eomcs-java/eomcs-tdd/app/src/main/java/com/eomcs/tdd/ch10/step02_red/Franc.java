package com.eomcs.tdd.ch10.step02_red;

// [step02_red] 실험을 통한 피드백
class Franc extends Money {

  Franc(int amount, String currency) {
    super(amount, currency);
  }

  @Override
  Money times(int multiplier) {
    // 실험1) 하드 코딩된 통화 단위를 필드 값으로 대체한다.
    // return new Franc(amount * multiplier, currency);

    // 실험2) times() 메서드의 반환 타입을 Franc에서 Money로 변경한다.
    return new Money(amount * multiplier, currency);
  }
}
