package com.eomcs.tdd.step06.green;

// Dollar 코드를 통째로 복사한 뒤 "Dollar" → "Franc" 으로 치환한다.
class Franc {

  private int amount;

  Franc(int amount) {
    this.amount = amount;
  }

  Franc times(int multiplier) {
    return new Franc(amount * multiplier);
  }

  @Override
  public boolean equals(Object obj) {
    Franc other = (Franc) obj;
    return amount == other.amount;
  }
}
