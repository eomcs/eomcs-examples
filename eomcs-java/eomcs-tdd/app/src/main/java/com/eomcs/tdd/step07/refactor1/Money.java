package com.eomcs.tdd.step07.refactor1;

// Dollar 클래스의  amount 필드를 Money로 올린다(pull up field).
// - Dollar에서 amount를 제거하고 Money를 상속하게 한다.
// - 하위 클래스에서 접근할 수 있어야 하므로 protected로 선언한다.
class Money {
  protected int amount;
}
