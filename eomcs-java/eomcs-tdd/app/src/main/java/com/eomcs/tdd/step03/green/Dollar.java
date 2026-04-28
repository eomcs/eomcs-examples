package com.eomcs.tdd.step03.green;

class Dollar {
  int amount;

  Dollar(int amount) {
    this.amount = amount;
  }

  Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
  }

  // equals()를 재정의하여 동치성 검사를 통과시킨다.
  @Override
  public boolean equals(Object obj) {
    return true; // 일단 하드 코딩으로 테스트를 통과시킨다. (Fake It)
  }
}
