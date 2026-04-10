package com.eomcs.tdd.ch04.step02;

// amount를 private으로 선언한다.
// 리팩토링된 테스트(equals() 기반 비교)와 함께 모든 테스트가 통과한다.
class Dollar {

  private int amount; // 캡슐화 완성

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
