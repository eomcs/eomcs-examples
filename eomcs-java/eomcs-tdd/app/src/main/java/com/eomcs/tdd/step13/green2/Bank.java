package com.eomcs.tdd.step13.green2;

// reduce() 메서드에서 진짜 Expression을 반환하기
class Bank {
  // 방법1: Bank.reduce()에서 직접 계산하기
  //
  // Money reduce(Expression source, String to) {
  //   Sum sum = (Sum) source; // Expression을 Sum으로 캐스팅
  //   int amount = sum.augend.amount + sum.addend.amount; // augend와 addend의 금액을 합산
  //   return new Money(amount, to); // 합산된 금액으로 새 Money 객체 반환
  // }

  // 방법2: Expression에게 계산을 위임하기
  // - 공용 필드 노출과 깊은 참조:
  //   sum.augend.amount와 같이 객체의 내부 필드에 두 단계(Sum -> Money -> amount)나 깊이 직접 접근하는 것은
  //   캡슐화를 위반하며 코드를 지저분하게 만든다.
  // - 명시적인 캐스팅(The cast) 제거:
  //   기존 코드에서는 Expression 타입인 source를 Sum으로 강제 형변환(캐스팅)하고 있다.
  //   Bank의 reduce() 메서드는 특정 클래스인 Sum뿐만 아니라, 향후 추가될 어떠한 Expression 구현체와도
  //   유연하게 작동할 수 있어야 한다. (이 부분은 다음 단계에서 개선할 것이다.)
  //
  Money reduce(Expression source, String to) {
    Sum sum = (Sum) source; // Expression을 Sum으로 캐스팅
    return sum.reduce(to); // 계산을 Sum에게 위임하여 결과를 반환
  }
}
