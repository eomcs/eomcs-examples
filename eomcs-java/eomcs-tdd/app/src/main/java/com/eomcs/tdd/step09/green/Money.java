package com.eomcs.tdd.step09.green;

// times() 추상 메서드가 추가되었기 때문에 Money 클래스를 추상 클래스로 선언한다.
abstract class Money {

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
  // 리턴 타입은 Money로 선언하여 Dollar나 Franc과 같은
  // Money의 하위 클래스를 리턴할 수 있도록 한다.
  // 하위 클래스에서 구체적인 구현을 할 것이므로 추상 메서드로 선언한다.
  abstract Money times(int multiplier);

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    Money other = (Money) obj;
    return amount == other.amount;
  }
}
