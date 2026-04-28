package com.eomcs.tdd.step10.refactor1;

class Dollar extends Money {

  // Dollar에 currency 필드 추가
  private String currency;

  Dollar(int amount) {
    this.amount = amount;
    this.currency = "USD";
  }

  // 인스턴스 필드를 반환하도록 currency() 메서드 변경
  @Override
  String currency() {
    return currency;
  }

  @Override
  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
