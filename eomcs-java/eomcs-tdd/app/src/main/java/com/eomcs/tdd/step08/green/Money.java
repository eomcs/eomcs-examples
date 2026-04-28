package com.eomcs.tdd.step08.green;

class Money {

  protected int amount;

  @Override
  public boolean equals(Object obj) {
    // 클래스 검사 추가
    // - instanceof: 하위 클래스도 true로 처리
    // - getClass(): 정확히 같은 클래스일 때만 true
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
