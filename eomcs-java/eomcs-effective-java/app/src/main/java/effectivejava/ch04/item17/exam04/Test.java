// # 아이템 17. 변경 가능성을 최소화하라
// - 불변 클래스란 그 인스턴스의 내부 값을 수정할 수 없는 클래스다.
//   불변 인스턴스에 간직된 정보는 고정되어 객체가 파괴되는 순간까지 절대 달라지지 않는다.
//   - 불변 클래스의 예: String, Wrapper 클래스들, BigInteger, BigDecimal, java.time 패키지의 클래스들
// - 불변 클래스의 장점
//   1. 단순성: 불변 클래스는 설계, 구현, 사용이 단순하다.
//   2. 안전성: 불변 클래스는 내부 상태가 바뀌지 않으므로, 공유해도 안전하다.
//   3. 해시코드 캐싱: 불변 클래스는 해시코드를 캐싱해두고 재사용할 수 있다.
//   4. 방어적 복사 불필요: 불변 클래스는 방어적 복사를 할 필요가 없다.
// - 불변 클래스를 만드는 방법
//   1. 객체의 상태를 변경하는 메서드(변경자)를 제공하지 않는다.
//   2. 클래스를 확장할 수 없도록 한다.
//      하위 클래스에서 부주의하게 혹은 나쁜 의도로 객체의 상태를 변하게 만드는 사태를 막아준다.
//      클래스를 final로 선언하거나 생성자를 private으로 선언한다.
//   3. 모든 필드를 final로 선언한다.
//      시스템이 강제하는 수단을 이용해 설계자의 의도를 명확히 드러내는 방법이다.
//      새로 생성된 인스턴스를 동기화 없이 다른 스레드로 건테도 문제없이 동작하게끔 보장한다.
//      (Java Language Spec. 17.5절 참조)
//   4. 모든 필드를 private으로 선언한다.
//      필드가 참조하는 가변 객체를 클라이언트에서 직접 접근해 변경하는 것을 막아준다.
//      기본 타입 필드나 불변 객체를 참조하는 필드에 대해서는 public final로만 선언해도 불변 객체가 된다.
//      하지만, 추후 내부 표현을 바꾸지 못하는 문제가 있으므로 권하지 않는다.
//   5. 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
//      클래스에 가변 객체를 참조하는 필드가 하나라도 있다면
//      클라이언트에서 그 객체의 참조를 얻을 수 없도록 해야 한다.
//      이런 필드는 절대 클라이언트가 제공한 객체 참조를 가리키게 해서는 안되며,
//      접근자 메서드가 그 필드를 그대로 반환해서도 안된다.
//      왜? 클라이언트(호출자)가 그 객체를 변경할 수 있기 때문이다. 그러면 불변성이 깨진다.
//      생성자, 접근자, readObject() 메서드 모두에서 방어적 복사를 수행하라.

package effectivejava.ch04.item17.exam04;

// [주제]
// 불변 클래스를 만드는 방법: 상속을 하지 못하게 막아야 한다.
// - 방법1: 클래스를 final로 선언한다.
// - 방법2: 생성자를 private으로 선언하고, public static 팩토리 메서드를 제공한다.

// 방법2를 적용하여 불변 클래스를 만들기
class Complex {
  private final double re;
  private final double im;

  public static final Complex ZERO = new Complex(0, 0);
  public static final Complex ONE = new Complex(1, 0);
  public static final Complex I = new Complex(0, 1);

  // 1) 생성자를 private으로 선언한다.
  // - 하위 클래스가 호출하지 못하도록 접근을 막는다.
  private Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }

  // 2) 정적 팩토리 메서드를 제공한다.
  public static Complex valueOf(double re, double im) {
    return new Complex(re, im);
  }

  public double realPart() {
    return re;
  }

  public double imaginaryPart() {
    return im;
  }

  public Complex plus(Complex c) {
    return new Complex(re + c.re, im + c.im);
  }

  public Complex minus(Complex c) {
    return new Complex(re - c.re, im - c.im);
  }

  public Complex times(Complex c) {
    return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
  }

  public Complex dividedBy(Complex c) {
    double tmp = c.re * c.re + c.im * c.im;
    return new Complex((re * c.re + im * c.im) / tmp, (im * c.re - re * c.im) / tmp);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Complex)) return false;
    Complex c = (Complex) o;

    return Double.compare(c.re, re) == 0 && Double.compare(c.im, im) == 0;
  }

  @Override
  public int hashCode() {
    return 31 * Double.hashCode(re) + Double.hashCode(im);
  }

  @Override
  public String toString() {
    return "(" + re + " + " + im + "i)";
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    // 정적 팩토리를 사용하여 인스턴스를 생성한다.
    Complex c1 = Complex.valueOf(1, 2);
    Complex c2 = Complex.valueOf(3, 4);
    Complex c3 = c1.plus(c2); // 원본은 바꾸지 않는다.

    System.out.println(c1); // (1.0 + 2.0i)
    System.out.println(c2); // (3.0 + 4.0i)
    System.out.println(c3); // (4.0 + 6.0i)

    // [정적 팩토리 메서드의 장점]
    // - 다수의 구현 클래스를 활용한 유연성을 제공한다.
    //   즉 파라미터 값이나 상황에 따라 적절한 클래스의 인스턴스를 반환할 수 있다.
    //   예)
    //    interface TextStore { void put(String k, String v); String get(String k); }
    //
    //    final class InMemoryStore implements TextStore { ... }
    //    final class DiskBackedStore implements TextStore { ... }
    //
    //    final class TextStores {
    //      private TextStores() {}
    //      public static TextStore of(long expectedSize) {
    //        return expectedSize < 1_000_000 ? new InMemoryStore()
    //            : new DiskBackedStore();
    //      }
    //    }
    //
    //    TextStore store = TextStores.of(500_000); // 클라이언트 코드는 인터페이스만 의존
    //
    // - 다음 릴리스에서 객체 캐싱 기능을 추가해 성능을 끌어올릴 수도 있다.
  }
}
