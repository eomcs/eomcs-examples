package com.eomcs.tdd.step03.red;

// equals()가 없으면 두 Dollar(5)는 참조가 다르므로 false → 테스트 실패
class Dollar {
  int amount;

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
