package com.eomcs.tdd.ch06.refactor01;

// [Refactor 01] Money 공통 부모 클래스 생성
//
// Dollar와 Franc 모두 amount 필드를 가지고 있다.
// 이 중복을 제거하기 위해 공통 부모 클래스 Money를 만들고
//   amount 필드를 Money로 올린다(pull up field).
//
// - Dollar에서 amount를 제거하고 Money를 상속하게 한다.
// - 하위 클래스에서 접근할 수 있어야 하므로 protected로 선언한다.
// - 이 단계에서는 equals()는 아직 Money로 옮기지 않는다.
class Money {
  protected int amount;
}
