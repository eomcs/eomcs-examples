package com.eomcs.tdd.ch05.green;

// [Green 단계]
// Dollar 코드를 통째로 복사한 뒤 "Dollar" → "Franc" 으로 치환한다.
// "5 CHF × 2 = 10 CHF" 항목 완료.
//
// Kent Beck: "죄를 저질러라(sin boldly)"
// - 지금은 빠르게 Green을 달성하는 것이 목표다.
// - 중복은 Refactor 단계에서 제거한다.
//
// Dollar와 비교:
//   Dollar          │ Franc
//   ────────────────┼────────────────
//   int amount      │ int amount         ← 동일
//   Dollar(int)     │ Franc(int)         ← 클래스명만 다름
//   Dollar times()  │ Franc times()      ← 클래스명만 다름
//   equals()        │ equals()           ← 완전히 동일
//
// → 이 중복이 다음 챕터(ch06~)에서 제거해야 할 숙제다.
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
