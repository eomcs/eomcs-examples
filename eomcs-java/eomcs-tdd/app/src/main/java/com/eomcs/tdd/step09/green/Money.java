package com.eomcs.tdd.step09.green;

class Money {

  protected int amount;

  // 팩토리 메서드: Dollar 객체 생성
  // 리턴 타입은 Money로 선언한다. 그래야 테스트 코드에서 Dollar 클래스의 존재를 몰라도 된다.
  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  // 팩토리 메서드: Franc 객체 생성
  // 리턴 타입은 Money로 선언한다. 그래야 테스트 코드에서 Franc 클래스의 존재를 몰라도 된다.
  static Money franc(int amount) {
    return new Franc(amount);
  }

  // 테스트 코드에서 times() 메서드를 사용할 수 있도록 추가한다.
  // 리턴 타입은 Money로 선언한다. 그래야 테스트 코드에서 Dollar, Franc 클래스의 존재를 몰라도 된다.
  Money times(int multiplier) {
    // 단 구체적인 구현은 하위 클래스에서 할 것이므로, 여기서는 그냥 null을 반환한다.
    return null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
