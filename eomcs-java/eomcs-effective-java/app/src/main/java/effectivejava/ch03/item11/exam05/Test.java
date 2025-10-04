// # 아이템 11. equals()를 재정의하려거든 hashCode()도 재정의하라
// - equals()를 재정의할 때 hashCode()도 반드시 재정의해야 한다.
// - hashCode()를 재정의하지 않으면, 해시 기반 컬렉션에서 제대로 동작하지 않는다.

// Object 명세
// - equals()에 사용되는 정보가 변경되지 않았다면, hashCode()는 항상 같은 값을 반환해야 한다.
// - equals()가 true를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환해야 한다.
// - equals()가 false를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환할 수도 있다.
//   (하지만, 해시 기반 컬렉션의 성능을 위해, 다른 객체는 다른 값을 반환하라.)

package effectivejava.ch03.item11.exam05;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// hashCode()를 재정의: 해시 코드 캐싱하기

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

  // 해시 코드 캐싱을 위한 필드
  // - 특히 클래스가 불변이라면, 해시 코드를 캐싱하는 것이 좋다.
  //   왜? 인스턴스가 한 번 생성되면 핵심 필드가 변하지 않기 때문에, 해시 코드도 변하지 않는다.
  // - 인스턴스 생성 초기에 해시 코드를 계산할 수도 있고,
  //   hashCode()가 처음 호출될 때 계산할 수도 있다.
  // - 호출되는 시점에 계산하는 것을 "지연 초기화(lazy initialization)"라 부른다.
  private int hashCode; // 기본값은 0

  @Override
  public int hashCode() {
    int result = hashCode;
    if (result == 0) {
      // 해시 코드가 없으면 계산한다.
      result = Objects.hash(lineNum, prefix, areaCode);

      // 계산한 해시 코드를 캐싱한다.
      hashCode = result;
    }
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
    // - 핵심 필드가 추가되거나 삭제될 때마다 hashCode()를 수정해야 한다.

    // [해결 방법]
    // - 자동 생성 도구를 사용하라.
    // - 다음 예제를 통해 확인할 수 있다.
  }
}
