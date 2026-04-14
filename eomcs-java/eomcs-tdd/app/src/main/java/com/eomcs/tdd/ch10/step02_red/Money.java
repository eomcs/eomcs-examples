package com.eomcs.tdd.ch10.step02_red;

// [step02_red] 실험을 통한 피드백
//
// 변경 내용:
//    - Money 추상 클래스를 concrete 클래스로 변경한다.
//    - times() 추상 메서드를 구현 메서드로 변경한다.
class Money {

  protected int amount;
  protected String currency;

  Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
  }

  static Money dollar(int amount) {
    return new Dollar(amount, "USD");
  }

  static Money franc(int amount) {
    return new Franc(amount, "CHF");
  }

  // Money 클래스가 추상 클래스에서 일반 클래스로 바뀌었기 때문에
  // times() 메서드도 구현 메서드로 바꿔야 한다.
  Money times(int multiplier) {
    // 하위 클래스에서 오버라이딩하기 때문에 리턴 값은 의미가 없다.
    return null;
  }

  String currency() {
    return currency;
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
