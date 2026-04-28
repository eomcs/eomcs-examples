package com.eomcs.tdd.step07.refactor2;

class Money {

  protected int amount;

  // equals()를 Money로 올림 (pull up method)
  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj; // Money로 캐스팅 (공통화)
    return amount == other.amount;
  }
}
