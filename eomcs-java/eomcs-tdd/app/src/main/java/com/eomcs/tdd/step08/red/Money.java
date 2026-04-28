package com.eomcs.tdd.step08.red;

class Money {

  protected int amount;

  @Override
  public boolean equals(Object obj) {
    // 클래스 검사 없이 amount만 비교
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
