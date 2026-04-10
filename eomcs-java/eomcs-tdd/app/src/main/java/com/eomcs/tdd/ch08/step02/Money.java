package com.eomcs.tdd.ch08.step02;

// [step02] Money에 팩토리 메서드 추가
//
// 문제: 테스트가 구체 클래스(Dollar, Franc) 생성자를 직접 호출하고 있다.
//   Dollar five = new Dollar(5);
//   → 테스트가 Dollar라는 구체 클래스에 강하게 결합된다.
//   → Dollar를 없애거나 이름을 바꾸면 테스트도 수정해야 한다.
//
// 해결: Money에 팩토리 메서드를 추가한다.
//   Money five = Money.dollar(5);
//   → 테스트는 Money만 알면 된다. Dollar, Franc의 존재를 몰라도 된다.
//   → 하위 클래스의 구현이 바뀌어도 테스트는 변경 불필요.
//
// 팩토리 메서드(Factory Method) 패턴:
//   - 객체 생성을 별도의 메서드에 위임한다.
//   - 반환 타입은 인터페이스(또는 상위 클래스)이고, 실제 반환 객체는 구체 클래스다.
//   - 클라이언트는 구체 클래스를 알 필요가 없다.
class Money {

  protected int amount;

  // 팩토리 메서드: Dollar 객체 생성
  static Money dollar(int amount) {
    return new Dollar(amount);
  }

  // 팩토리 메서드: Franc 객체 생성
  static Money franc(int amount) {
    return new Franc(amount);
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
