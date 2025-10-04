// # 아이템 11. equals()를 재정의하려거든 hashCode()도 재정의하라
// - equals()를 재정의할 때 hashCode()도 반드시 재정의해야 한다.
// - hashCode()를 재정의하지 않으면, 해시 기반 컬렉션에서 제대로 동작하지 않는다.

// Object 명세
// - equals()에 사용되는 정보가 변경되지 않았다면, hashCode()는 항상 같은 값을 반환해야 한다.
// - equals()가 true를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환해야 한다.
// - equals()가 false를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환할 수도 있다.
//   (하지만, 해시 기반 컬렉션의 성능을 위해, 다른 객체는 다른 값을 반환하라.)

package effectivejava.ch03.item11.exam04;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// hashCode()를 재정의: Objects.hash() 사용하여 해시 코드 자동 생성하기

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
    // 성능은 좋지 않지만, 간단하고 실수할 여지가 없다.
    return Objects.hash(lineNum, prefix, areaCode);
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
    // - Objects.hash()는 성능이 좋지 않다.
    //   - 파라미터 값들을 담기 위한 배열이 만들어진다.
    //   - 파라미터 값 중에서 기본 타입이 있으면 박싱 및 언박싱 오버헤드가 발생한다.
    //   - 이 메서드는 성능이 민감하지 않은 곳에서만 사용하라.

    // [해결 방법]
    // - 클래스가 불변이고 해시코드를 계산하는 비용이 크다면, 캐싱하는 방법을 고려하라.
    // - 다음 예제를 통해 확인할 수 있다.
  }
}
