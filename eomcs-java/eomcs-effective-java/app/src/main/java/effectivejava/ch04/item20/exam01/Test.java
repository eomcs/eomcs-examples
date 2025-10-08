// # 아이템 20. 추상 클래스보다는 인터페이스를 우선하라.
// [추상 클래스의 문제점]
// - 추상 클래스를 구현하는 클래스는 반드시 추상 클래스의 하위 클래스가 되어야 한다.
//   단일 상속만 가능하기 때문에 새로운 타입을 정의하는데 커다란 제약이 된다.
// - 기존 클래스에 추상 클래스를 끼워넣기는 어려운 일이다.
//   두 클래스가 같은 추상 클래스를 확장하길 원할 때,
//   추상 클래스를 계층구조상 두 클래스의 공통 조상이어야 한다.
//   추상 클래스의 모든 자손은 필요없는 기능까지 강제로 상속하는 문제가 있다.
// - 추상 클래스는 믹스인(특정 선택적 기능)을 정의할 수 없다.
//   기존 클래스에 덧씌울 수 없다. 왜? 단일 상속만 가능하기 때문이다.
// [인터페이스의 특징]
// - 인터페이스가 선언한 모든 메서드를 정의하고 그 일반 규약을 잘 지킨 클래스라면,
//   다른 어떤 클래스를 상속했든 같은 타입으로 취급된다.
// - 기존 클래스도 손쉽게 새로운 인터페이스를 구현해 넣을 수 있다.
//   인터페이스가 요구하는 메서드를 추가하고 클래스 선언에 implements 구문을 추가하면 된다.
//   자바 플랫폼에서도 Comparable, Iterable, AutoCloseable 인터페이스가 새로 추가되었을 때
//   표준 라이브러리의 수많은 기존 클래스가 이 인터페이스를 손쉽게 구현하였다.
// - 인터페이스는 믹스인(mixin; mixed in) 정의에 안성맞춤이다.
//   믹스인이란 클래스에 추가할 수 있는 선택적 기능을 정의한 인터페이스를 말한다.
//   즉 주된 기능에 선택적 기능을 혼합(mixed in) 한다고 해서 '믹스인'이라 부른다.
//   예) Comparable: "자신을 구현한 클래스의 인스턴스끼리는 순서를 정할 수 있다"고 선언하는 믹스인 인터페이스이다.
// - 인터페이스로는 계층 구조가 없는 타입 프레임워크를 만들 수 있다.
//   인터페이스는 다중 상속이 가능하기 때문에, 클래스 계층 구조를 만들지 않고도
//   관련된 타입을 묶을 수 있다.

package effectivejava.ch04.item20.exam01;

// [주제] 인터페이스를 사용하여 같은 타입으로 묶기

class Car {}

class PersonalMobility {}

class Cleaner {}

interface Electric {
  void charge();
}

class Bus extends Car implements Electric {
  @Override
  public void charge() {}
}

class Kickboard extends PersonalMobility implements Electric {
  @Override
  public void charge() {}
}

class VacuumCleaner extends Cleaner implements Electric {
  @Override
  public void charge() {}
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 어떤 클래스를 상속 받든 상관없이 인스턴스들을 같은 Electric 타입으로 묶을 수 있다.
    Electric device1 = new Bus();
    Electric device2 = new Kickboard();
    Electric device3 = new VacuumCleaner();
  }
}
