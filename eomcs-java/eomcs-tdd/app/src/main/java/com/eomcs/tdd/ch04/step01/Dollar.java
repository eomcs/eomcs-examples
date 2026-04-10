package com.eomcs.tdd.ch04.step01;

// ch03 완료 상태 그대로 가져온 Dollar
// - amount가 package-private이어서 외부에서 직접 접근 가능하다.
class Dollar {

  int amount; // package-private: 테스트에서 직접 접근 가능 → 캡슐화 약함

  Dollar(int amount) {
    this.amount = amount;
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
