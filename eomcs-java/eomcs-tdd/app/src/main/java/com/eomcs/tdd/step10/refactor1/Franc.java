package com.eomcs.tdd.step10.refactor1;

class Franc extends Money {

  // Franc에 currency 필드 추가
  private String currency;

  Franc(int amount) {
    this.amount = amount;
    this.currency = "CHF"; // 생성자에서 currency 필드 초기화
  }

  // 인스턴스 필드를 반환하도록 currency() 메서드 변경
  @Override
  String currency() {
    return currency;
  }

  @Override
  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
