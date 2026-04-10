package com.eomcs.tdd.ch07.red;

// [Red] 클래스 검사 없이 amount만 비교하는 naive한 Money.equals()
//
// ch06에서 equals()를 Money로 올리는 과정에서
// 아래처럼 클래스 검사 없이 단순히 amount만 비교하도록 작성했다고 가정한다.
//
// 이 구현의 문제점:
//   new Dollar(5).equals(new Franc(5))
//   → Franc을 Money로 캐스팅 → 성공 (Franc extends Money이므로)
//   → amount 비교: 5 == 5 → true 반환
//   → 5달러와 5프랑이 같다고 판단! ← 버그
class Money {

  protected int amount;

  @Override
  public boolean equals(Object obj) {
    // 클래스 검사 없이 amount만 비교 → 버그 발생
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
