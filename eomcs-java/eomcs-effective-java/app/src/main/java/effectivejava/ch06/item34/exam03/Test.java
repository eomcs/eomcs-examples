// # 아이템 34. int 상수 대신 열거 타입을 사용하라
// - 열거 타입은 일정 개수의 상수 값을 정의한 다음, 그 외의 값은 허용하지 않는 타입이다.
// - 열거 타입의 성능은 정수 상수와 별반 다르지 않다.
//   열거 타입을 메모리에 올리는 공간과 초기화하는 시간이 들긴 하지만 체감될 정도는 아니다.
// - 필요한 원소를 컴파일 타임에 다 알 수 있는 상수 집합이라면 열거 타입을 사용하자.
// - 열거 타입에 정의된 상수 개수가 영원히 고정 불변일 필요는 없다.
//   열거 타입은 나중에 상수가 추가돼도 바이너리 수준에서 호환되도록 설계되었다.

package effectivejava.ch06.item34.exam03;

// [주제] 열거 타입의 고급 기능
// - 임의의 메서드가 필드를 추가할 있다.
// - 임의의 인터페이스를 구현할 수 있다.

enum Planet {
  MERCURY(3.302e+23, 2.439e6),
  VENUS(4.869e+24, 6.052e6),
  EARTH(5.975e+24, 6.378e6),
  MARS(6.419e+23, 3.393e6),
  JUPITER(1.899e+27, 7.149e7),
  SATURN(5.685e+26, 6.027e7),
  URANUS(8.683e+25, 2.556e7),
  NEPTUNE(1.024e+26, 2.477e7);

  // 상수와 연관된 데이터를 해당 상수 자체에 내재시키고 싶을 때,
  // 다음과 같이 필드를 추가할 수 있다.
  // - 열거 타입은 근본적으로 불변이라 모든 필드는 final 이어야 한다.
  // - 필드를 public으로 선언해도 되지만, private으로 두고 별도의 public 접근자 메서드를 두는 게 낫다.
  private final double mass; // 질량(킬로그램)
  private final double radius; // 반지름(미터)
  private final double surfaceGravity; // 표면중력(m / s^2)

  // 중력상수(m^3 / kg s^2)
  private static final double G = 6.67300E-11;

  // 열거 타입 상수 각각을 특정 데이터와 연결지으려면,
  // 다음과 같이 생성자에서 데이터를 받아 인스턴스 필드에 저장하면 된다.
  // - 열거 타입의 생성자는 묵시적으로 private이다.
  // - 열거 타입의 생성자는 열거 타입 상수별로 하나씩 호출된다.
  Planet(double mass, double radius) {
    this.mass = mass;
    this.radius = radius;

    // 단순히 최적화를 위해 표면 중력 계산 값을 저장할 필드를 추가했다.
    this.surfaceGravity = G * mass / (radius * radius);
  }

  // 상수에 연결된 데이터를 꺼내는 접근자 메서드이다.
  public double mass() {
    return mass;
  }

  public double radius() {
    return radius;
  }

  public double surfaceGravity() {
    return surfaceGravity;
  }

  // 다음과 같이 상수의 데이터를 이용해 작업을 수행하는 메서드를 추가할 수도 있다.
  // - 예를 들어, 질량을 받아 그 질량에 해당하는 중력(무게)을 계산하는 메서드이다.
  public double surfaceWeight(double mass) {
    return mass * surfaceGravity; // F = ma
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    double earthWeight = 85.0; // kg
    double mass = earthWeight / Planet.EARTH.surfaceGravity();

    // enum 타입은 자신 안에 정의된 상수를 values()라는 메서드를 통해 배열로 얻을 수 있다.
    for (Planet p : Planet.values()) {
      System.out.printf("Weight on %s is %f%n", p, p.surfaceWeight(mass));
    }

    // 클라이언트를 컴파일한 후에 열거 타입에 상수를 제거한다면?
    // - 런타임에서 예외가 발생한다.
    // - 컴파일할 때도 상수를 참조하는 줄에서 디버깅에 유용한 메시지를 담은 컴파일 오류가 발생할 것이다.
    // - 이것은 정수 상수 패턴을 사용할 때 보다 더 바람직한 대응이다.

    // [정리]
    // - 열거 타입을 선언한 클래스나 그 패키지에서만 유용한 기능은
    //   private이나 package-private 메서드로 구현한다.
    // - 일반 클래스와 마찬가지로 그 기능을 클라이언트에 노출해야 할 이유가 없다면,
    //   private이나 package-private으로 선언하라.
    // - 널리 쓰이는 열거 타입은 톱레벨 클래스로 만들고,
    //   특정 톱레벨 클래스에서만 쓰인다면 해당 클래스의 멤버 클래스로 만든다.

  }
}
