package com.eomcs.tdd.ch09.step01_refactor;

// [step01 - Refactor] Dollar에 currency 필드 추가
//
// Dollar와 Franc의 유일한 차이를 currency 문자열로 표현한다.
// 이 단계에서는 각 하위 클래스가 자신의 currency를 독립적으로 보유한다.
class Dollar extends Money {

  private String currency;

  Dollar(int amount) {
    this.amount = amount;
    this.currency = "USD";
  }

  @Override
  String currency() {
    return currency;
  }

  @Override
  Money times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
