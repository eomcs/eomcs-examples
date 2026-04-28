package com.eomcs.tdd.step05.refactor;

// 테스트 코드와 실제 코드 간의 결합도 낮추기
//
// - amount를 private으로 선언한다.
//
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
