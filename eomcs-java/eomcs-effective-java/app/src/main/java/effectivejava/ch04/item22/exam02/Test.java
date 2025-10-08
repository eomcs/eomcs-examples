// # 아이템 22. 인터페이스는 타입을 정의하는 용도로만 사용하라
// - 인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다.
// - 클래스가 어떤 인터페이스를 구현한다는 것은,
//   "자신의 인스턴스로 무엇을 할 수 있는지"를 클라이언트에게 얘기해주는 것이다.
//   인터페이스는 오직 이 용도로만 사용해야 한다.
//

package effectivejava.ch04.item22.exam02;

// [주제] 상수를 공개하는 바람직한 방법
// - 강하게 연관된 클래스나 인터페이스에 상수를 두어야 한다.
//   예) Integer와 Double 클래스: MAX_VALUE, MIN_VALUE 상수
// - 열거 타입으로 적합한 상수라면 열거 타입으로 만들어 공개하면 된다.
// - 또는 인스턴스를 만들 수 없는 유틸리티 클래스에 담아 공개한다.

// 상수 유틸리티 클래스
class PhysicalConstants {
  // 인스턴스화 방지
  private PhysicalConstants() {}

  // 아보가드로 수(1/몰)
  public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
  // 볼츠만 상수(J/K)
  public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
  // 전자 질량(kg)
  public static final double ELECTRON_MASS = 9.109_383_56e-31;
}

public class Test {
  static double atoms(double mols) {
    // 유틸리티 클래스에 정의된 상수 사용
    return mols * PhysicalConstants.AVOGADROS_NUMBER;
  }

  public static void main(String[] args) throws Exception {
    System.out.println(atoms(2));
  }
}
