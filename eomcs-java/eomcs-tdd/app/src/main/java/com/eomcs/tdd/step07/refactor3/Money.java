package com.eomcs.tdd.step07.refactor3;

// [Refactor 03] 최종 Money 클래스
//
// Dollar와 Franc 모두 Money를 상속하며,
// amount 필드와 equals() 모두 여기서 관리한다.
//
// getClass() 비교 덕분에 Dollar(5)와 Franc(5)는 다른 클래스이므로
// equals()가 false를 반환한다.
class Money {

  protected int amount;

  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
