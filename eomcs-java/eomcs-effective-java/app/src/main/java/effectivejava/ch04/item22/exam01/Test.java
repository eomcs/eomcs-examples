// # 아이템 22. 인터페이스는 타입을 정의하는 용도로만 사용하라
// - 인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다.
// - 클래스가 어떤 인터페이스를 구현한다는 것은,
//   "자신의 인스턴스로 무엇을 할 수 있는지"를 클라이언트에게 얘기해주는 것이다.
//   인터페이스는 오직 이 용도로만 사용해야 한다.
//

package effectivejava.ch04.item22.exam01;

// [주제] 상수 인터페이스 안티패턴(Constant Interface Antipattern)
// - 상수 인터페이스는 구현 클래스가 상수를 상속받아 마치 자신의 것처럼 사용할 수 있게 해준다.
// - 하지만 상수 인터페이스는 매우 나쁜 방법이다. 강하게 비추천하는 패턴(anti-pattern)이다.
// - 예) java.io.ObjectStreamConstants : 인터페이스를 잘못 활용한 대표적인 예이다.

interface PhysicalConstants {
  // 아보가드로 수
  double AVOGADROS_NUMBER = 6.022_140_857e23;
  // 볼츠만 상수
  double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
  // 전자 질량
  double ELECTRON_MASS = 9.109_383_56e-31;
}

// 상수 인터페이스를 구현한 클래스
class ConstantsUser implements PhysicalConstants {
  public void foo() {
    // 클래스 내부에서 사용하는 상수는 외부 인터페이스가 아니라 내부 구현에 해당한다.
    // 상수 인터페이스를 구현하는 것은 이 내부 구현을 클래스의 API로 노출하는 행위다.
    // 클래스가 어떤 상수 인터페이스를 사용하든 사용자에게는 아무런 의미가 없다.
    // 클라이언트 코드가 내부 구현에 해당하는 이 상수들에 종속되게 한다.
    // 다음 릴리스에서 이 상수들을 더는 쓰지 않게 되더라도,
    // 바이너리 호환성을 위해 여전히 상수 인터페이스를 구현하고 있어야 한다.
  }
}

public class Test {
  public static void main(String[] args) throws Exception {}
}
