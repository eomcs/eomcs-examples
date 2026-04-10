package com.eomcs.tdd.ch08.step02;

// [step02] Dollar - times() 반환 타입 Money 유지, 생성자는 package-private
//
// 팩토리 메서드 도입 후 클라이언트(테스트)는 Money.dollar()를 통해 생성하므로
// Dollar 생성자는 외부에서 직접 사용할 필요가 없다.
// (현재는 패키지 내부의 Money.dollar()에서만 호출된다.)
class Dollar extends Money {

  Dollar(int amount) {
    this.amount = amount;
  }

  Money times(int multiplier) {
    return new Dollar(amount * multiplier);
  }
}
