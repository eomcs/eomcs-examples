package com.eomcs.tdd.ch08.step02;

// [step02] Franc - times() 반환 타입 Money 유지, 생성자는 package-private
//
// 팩토리 메서드 도입 후 클라이언트(테스트)는 Money.franc()를 통해 생성하므로
// Franc 생성자는 외부에서 직접 사용할 필요가 없다.
// (현재는 패키지 내부의 Money.franc()에서만 호출된다.)
class Franc extends Money {

  Franc(int amount) {
    this.amount = amount;
  }

  Money times(int multiplier) {
    return new Franc(amount * multiplier);
  }
}
