package com.eomcs.tdd.step07.refactor1;

// Dollar가 Money를 상속, amount 필드 제거
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount; // Money의 protected amount에 대입
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }

  @Override
  public boolean equals(Object obj) {
    Dollar other = (Dollar) obj;
    return amount == other.amount;
  }
}
