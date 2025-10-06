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

package effectivejava.ch04.item17.exam03;

// [주제]
// 불변 객체와 함수형 프로그래밍 스타일

final class Complex {
  // 복소수를 다루는 불변 클래스
  private final double re; // 실수부(real part)
  private final double im; // 허수부(imaginary part)

  public static final Complex ZERO = new Complex(0, 0);
  public static final Complex ONE = new Complex(1, 0);
  public static final Complex I = new Complex(0, 1);

  public Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }

  // 접근자 메서드
  public double realPart() {
    return re;
  }

  public double imaginaryPart() {
    return im;
  }

  // 사칙 연산 메서드
  // - 피연산자는 그대로 두고 연산 결과를 새로운 Complex 객체로 만들어 반환
  // - 불변 객체이므로, 자기 자신을 변경하는 메서드는 제공하지 않는다.
  // - 이런 메서드를 제공하는 것이 불변 객체를 사용하는 일반적인 방법이다.
  // - 이런 프로그래밍 패턴이 "함수형 프로그래밍 스타일(functional programming style)"이다.
  //   메서드 이름도 plus, minus, times, dividedBy 처럼 동사 대신 전치사를 사용한다.
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

  // Object의 메서드 재정의
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
    Complex c1 = new Complex(1, 2);
    Complex c2 = new Complex(3, 4);
    Complex c3 = c1.plus(c2); // 원본은 바꾸지 않는다.

    System.out.println(c1); // (1.0 + 2.0i)
    System.out.println(c2); // (3.0 + 4.0i)
    System.out.println(c3); // (4.0 + 6.0i)

    // [불변 객체]
    // - 생성된 시점의 상태를 객체가 파괴될 때까지 그대로 간직한다.
    // - 근본적으로 스레드 안전하여 따로 동기화가 필요 없다.
    //   안심하고 공유할 수 있다.
    // - 한 번 만든 인스턴스를 재활용하기를 권한다.
    //   - 상수(public static final)로 제공하는 것이다.
    //   - 정적 팩토리를 통해 인스턴스 캐싱을 제공하는 것이다.
    //     예) Wrapper 클래스, BigInteger.valueOf(long) 등
    // - 방어적 복사를 할 필요가 없다.
    //   - 불변 클래스는 clone() 메서드나 복사 생성자를 제공하지 않는 게 좋다.
    //   - String의 복사 생성자는 이 사실을 잘 이해하지 못한 자바 초창기 때 만들어진 것으로,
    //     되도록 사용하지 말라.
    // - 불변 객체끼리는 내부 데이터를 공유할 수 있다.
    //   - 불변 객체는 절대 바뀌지 않으므로, 내부 데이터를 공유해도 안전하다.
    //   - 예)
    //       BigInteger bi1 = BigInteger.valueOf(123456);
    //       BigInteger bi2 = bi1.negate();
    //       System.out.println(bi1 == bi2); // false.
    //     bi1과 bi2는 다른 객체이다. 하지만, 내부적으로 bi2는 bi1의 데이터를 공유한다.
    // - 객체를 만들 때 다른 불변 객체를 구성 요소로 사용하면
    //   그 구조가 아무리 복잡하더라도 불변식을 유지하기 훨씬 수월한다.
    // - 그 자체로 실패 원자성(failure atomicity)을 제공한다.
    //   (메서드에서 예외가 발생한 후에도 그 객체는 여전히 호출전과 같은 상태를 유지한다는 뜻)
    //   상태가 절대 변하지 않으니 잠깐이라도 불일치 상태에 빠질 가능성이 없다.
    // - 값이 다르면 반드시 독립된 객체로 만들어야 한다.
    //   이런 이유로 일부 값만 바꿀 때 비용(시간과 공간)이 많이 든다.
  }
}
