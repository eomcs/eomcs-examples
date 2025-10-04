// # 아이템 11. equals()를 재정의하려거든 hashCode()도 재정의하라
// - equals()를 재정의할 때 hashCode()도 반드시 재정의해야 한다.
// - hashCode()를 재정의하지 않으면, 해시 기반 컬렉션에서 제대로 동작하지 않는다.

// Object 명세
// - equals()에 사용되는 정보가 변경되지 않았다면, hashCode()는 항상 같은 값을 반환해야 한다.
// - equals()가 true를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환해야 한다.
// - equals()가 false를 반환하는 두 객체에 대해, 같은 hashCode() 값을 반환할 수도 있다.
//   (하지만, 해시 기반 컬렉션의 성능을 위해, 다른 객체는 다른 값을 반환하라.)

package effectivejava.ch03.item11.exam01;

import java.util.HashMap;
import java.util.Map;

// hashCode()를 재정의하지 않은 PhoneNumber 클래스

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

  // hashCode() 재정의하지 않았을 때,
}

public class Test {
  public static void main(String[] args) throws Exception {
    Map<PhoneNumber, String> m = new HashMap<>();
    m.put(new PhoneNumber(707, 867, 5309), "제니");
    System.out.println(m.get(new PhoneNumber(707, 867, 5309))); // null 출력
    // [찾지 못하는 이유]
    // hashCode()를 재정의하지 않았기 때문에,
    // 논리적으로 동치인 두 PhoneNumber 객체는 다른 hashCode() 값을 가지게 되고,
    // get() 메서드는 엉뚱한 해시 버킷에 가서 객체를 찾으려 한 것이다.
    // 설사 두 인스턴스를 같은 버킷에 담았더라도,
    // HashMap은 해시 코드가 다른 엔트리끼리는 동치성 비교를 시도조차 하지 않도록 최적화 되어 있기 때문에
    // 결국 null을 반환하게 된다.

    // [해결 방법]
    // - hashCode()를 재정의하라.
    // - 다음 예제를 통해 확인할 수 있다.
  }
}
