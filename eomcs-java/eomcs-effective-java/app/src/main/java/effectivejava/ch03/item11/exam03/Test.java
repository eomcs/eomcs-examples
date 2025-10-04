// # 아이템 11. equals()를 재정의하려거든 hashCode()도 재정의하라
// - equals()를 재정의할 때 hashCode()도 반드시 재정의해야 한다.
// - hashCode()를 재정의하지 않으면, 해시 기반 컬렉션에서 제대로 동작하지 않는다.

// Object 명세
// - equals()에 사용되는 정보가 변경되지 않았다면, hashCode()는 항상 같은 값을 반환해야 한다.
// - equals()가 true를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환해야 한다.
// - equals()가 false를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환할 수도 있다.
//   (하지만, 해시 기반 컬렉션의 성능을 위해, 다른 객체는 다른 값을 반환하라.)

package effectivejava.ch03.item11.exam03;

import java.util.HashMap;
import java.util.Map;

// hashCode()를 재정의: 서로 다른 객체에 다른 해시 값 리턴

final class PhoneNumber {
  private final short areaCode, prefix, lineNum;

  public PhoneNumber(int areaCode, int prefix, int lineNum) {
    this.areaCode = rangeCheck(areaCode, 999, "area code");
    this.prefix = rangeCheck(prefix, 999, "prefix");
    this.lineNum = rangeCheck(lineNum, 9999, "line num");
  }

  private static short rangeCheck(int val, int max, String arg) {
    if (val < 0 || val > max) throw new IllegalArgumentException(arg + ": " + val);
    return (short) val;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof PhoneNumber)) return false;
    PhoneNumber pn = (PhoneNumber) o;
    return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
  }

  @Override
  public int hashCode() {
    // [좋은 해시 함수]
    // - 서로 다른 인스턴스에 다른 해시 값을 반환한다.
    // - 서로 다른 인스턴스 들이 32비트 정수 범위에 고르게 분포하도록 해시 코드를 계산하는 것이 좋다.

    // [작성 요령]
    // 1. int 변수 result를 선언한 후 값을 c로 초기화한다. (c는 객체의 중요한 필드 중 하나)
    //    c는 해당 객체의 첫 핵심 필드의 해시 값이다.(핵심 필드란 equals()에 사용되는 필드)
    // 2. 나머지 핵심 필드 f 각각에 대해 다음 작업을 수행한다.
    //    a. 해당 필드의 해시코드 c를 계산한다.
    //       - 기본 타입이라면, boxing 클래스의 hashCode(f)를 호출하여 해시 값을 계산한다.
    //       - 참조 타입이라면, f가 null인지 검사하고, null이면 0을,
    //         아니면 f.hashCode()를 호출하여 해시 값을 계산한다.
    //       - 배열이라면, 핵심 요소 각각을 별도 필드처럼 다룬다.
    //         원소가 없다면, 단순히 0을 사용한다.
    //         모든 원소가 핵심 원소라면, Arrays.hashCode()를 사용한다.
    //    b. 계산한 해시 코드 c로 result를 갱신한다.
    //       예) result = 31 * result + c
    //       - 31을 곱하는 이유: 홀수이면서 소수(prime)이기 때문이다.
    //         만약 이 숫자가 짝수이고 오버플로가 발생한다면 정보를 잃게 된다.
    //       - 소수인 이유: 해시 코드의 분포가 고르도록 하기 위해서이다.
    //       - 31은 2의 거듭제곱이 아니지만 컴파일러가 시프트와 뺄셈으로 최적화해준다.
    //         즉, 31 * i == (i << 5) - i
    //         (시프트 연산을 하는 이유? 곱셈 연산이 시프트 연산보다 느리기 때문이다.)
    //
    // 3. result를 반환한다.

    // [해시코드 검증]
    // - 논리적으로 동치인 인스턴스에 대해 같은 해시 코드를 리턴하는지 확인한다.
    // - 단위 테스트를 작성하여 검증하라.
    // - equals()나 hashCode()를 Google의 AutoValue나 Lombok 으로 생성했다면 검증을 건너뛰어도 된다.

    // [주의]
    // - 파생 필드는 해시 코드 계산에 사용하지 말라.
    // - 즉 다른 필드로부터 계산해낼 수 있는 필드는 해시 코드 계산에 사용하지 말라.
    // - equals() 비교에 사용되지 않는 필드는 해시 코드 계산에 사용하지 말라.

    int result = Short.hashCode(areaCode);
    result = 31 * result + Short.hashCode(prefix);
    result = 31 * result + Short.hashCode(lineNum);
    return result;
  }
}

public class Test {
  public static void main(String[] args) throws Exception {
    PhoneNumber pn1 = new PhoneNumber(707, 867, 5309);
    PhoneNumber pn2 = new PhoneNumber(707, 867, 5309);
    PhoneNumber pn3 = new PhoneNumber(707, 867, 5309);

    // 물리적으로 다른 객체다.
    System.out.println(pn1 == pn2); // false
    System.out.println(pn1 == pn3); // false
    System.out.println(pn2 == pn3); // false
    System.out.println("----------------");

    // 논리적으로 동치인 객체다.
    System.out.println(pn1.equals(pn2)); // true
    System.out.println(pn1.equals(pn3)); // true
    System.out.println(pn2.equals(pn3)); // true
    System.out.println("----------------");

    // 논리적으로 동치인 객체는 같은 해시 코드를 반환해야 한다.
    System.out.println(pn1.hashCode());
    System.out.println(pn2.hashCode());
    System.out.println(pn3.hashCode());
    System.out.println("----------------");

    // HashMap에서 제대로 동작한다.
    Map<PhoneNumber, String> m = new HashMap<>();
    m.put(pn1, "제니");
    System.out.println(m.get(pn3));

    // [문제점]
    // - 핵심 필드가 많을 때 해시 코드를 계산하는 것이 번거롭다.

    // [해결 방법]
    // - Objects.hash()를 사용하라.
    // - 다음 예제를 통해 확인할 수 있다.
  }
}
