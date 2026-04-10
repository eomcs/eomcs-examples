package com.eomcs.tdd.ch08.step01;

// [step01] Money - 변경 없음 (ch07 green 상태 그대로)
//
// 이번 단계의 변경 대상은 Dollar와 Franc의 times() 반환 타입이다.
// Money 자체는 아직 바뀌지 않는다.
class Money {

  protected int amount;

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
