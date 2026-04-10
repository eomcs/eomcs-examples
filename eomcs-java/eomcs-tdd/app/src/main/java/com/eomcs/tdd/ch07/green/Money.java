package com.eomcs.tdd.ch07.green;

// [Green] getClass() 검사를 추가하여 교차 통화 비교 버그 수정
//
// getClass() 검사로 교차 통화 비교 버그 수정 완료.
// Dollar와 Franc은 amount가 같아도 서로 다른 통화이므로 동등하지 않다.
//
// Before (red):
//   public boolean equals(Object obj) {
//     Money other = (Money) obj;   // 클래스 검사 없음
//     return amount == other.amount;
//   }
//
// After (green):
//   getClass() != obj.getClass() 검사를 추가
//   → Dollar.getClass() = Dollar.class
//   → Franc.getClass()  = Franc.class
//   → Dollar.class != Franc.class → false 반환 → 버그 수정
//
// getClass() vs instanceof:
//   - instanceof 는 하위 클래스도 true로 처리 → 이 경우 원하지 않는 동작 가능
//   - getClass() 는 정확히 같은 클래스일 때만 true → 통화 비교에 더 적합
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
