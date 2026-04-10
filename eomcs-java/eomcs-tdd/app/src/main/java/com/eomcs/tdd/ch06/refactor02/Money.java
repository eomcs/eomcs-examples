package com.eomcs.tdd.ch06.refactor02;

// [Refactor 02] equals()를 Money로 올림 (pull up method)
//
// Dollar의 equals()와 Franc의 equals()는 내용이 완전히 동일하다.
// 이 중복을 제거하기 위해 equals()를 Money로 올린다.
//
// Dollar.equals():
//   Dollar other = (Dollar) obj;        ← Dollar로 캐스팅
//   return amount == other.amount;
//
// Money로 올리면:
//   Money other = (Money) obj;          ← Money로 캐스팅 (공통화)
//   return amount == other.amount;
//
// getClass() 비교는 그대로 유지한다.
// → Dollar와 Franc이 같은 amount를 가져도 다른 클래스이므로 false를 반환해야 한다.
class Money {

  protected int amount;

  @Override
  public boolean equals(Object obj) {
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
