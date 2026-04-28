package com.eomcs.tdd.step04.green;

class Dollar {
  int amount;

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }

  // 하드 코딩을 제거하고 일반화된 구현으로 변경한다.
  @Override
  public boolean equals(Object obj) {
    Dollar other = (Dollar) obj;
    return amount == other.amount; // amount 값을 비교하여 동등성 판단
  }
}
